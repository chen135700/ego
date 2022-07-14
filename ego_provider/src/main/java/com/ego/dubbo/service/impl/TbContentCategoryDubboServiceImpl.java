package com.ego.dubbo.service.impl;


import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.exception.DaoException;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectByPid(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andStatusEqualTo(1).andParentIdEqualTo(pid);
        example.setOrderByClause("sort_order asc");;
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbContentCategory category) throws DaoException {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(category.getName());
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if(list!=null && list.size()==0){
            int index = tbContentCategoryMapper.insert(category);
            if (index==1){
                TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(category.getParentId());
                if(!parentCategory.getIsParent()){
                    TbContentCategory parentUpdated = new TbContentCategory();
                    parentUpdated.setId(parentCategory.getId());
                    parentUpdated.setIsParent(true);
                    parentUpdated.setUpdated(category.getCreated());
                    int indexParent = tbContentCategoryMapper.updateByPrimaryKeySelective(parentUpdated);
                    if(indexParent!=1){
                        throw  new DaoException("新增类目-修改父节点失败");
                    }
                }
                return 1;
            }
        }
        throw  new DaoException("新增类目失败");
    }

    @Override
    public int updateNameById(TbContentCategory tbContentCategory) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(tbContentCategory.getName());
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if(list!=null&&list.size()==0){
            return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }
        return  0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) throws  DaoException{
        TbContentCategory category =new TbContentCategory();
        category.setId(id);
        Date date = new Date();
        category.setUpdated(date);
        category.setStatus(2);
        int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        if(index ==1){
            //判断当前节点的父节点是否还有其他正常状态的子节点
            TbContentCategory currContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(currContentCategory.getParentId()).andStatusEqualTo(1);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            if(list!=null&&list.size()==0){
                TbContentCategory parent = new TbContentCategory();
                parent.setIsParent(false);
                parent.setId(currContentCategory.getParentId());
                parent.setUpdated(date);
                int indexParent = tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
                if(indexParent!=1){
                    throw  new DaoException("删除分类-修改父分类失败");
                }
            }
            if(currContentCategory.getIsParent()){
                deleteChildrenById(id,date);
            }
            return 1;
        }
        throw new DaoException("删除策分类-失败");
    }


    private void deleteChildrenById(Long id , Date date)throws DaoException {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        for (TbContentCategory category : list) {
            TbContentCategory updateCategory = new TbContentCategory();
            updateCategory.setId(category.getId());
            updateCategory.setStatus(2);
            updateCategory.setUpdated(date);
            int index = tbContentCategoryMapper.updateByPrimaryKeySelective(updateCategory);
            if(index ==1){
                deleteChildrenById(category.getId(),date);
            }else {
                throw  new DaoException("删除分类-更新分类状态失败");
            }
        }
    }
}
