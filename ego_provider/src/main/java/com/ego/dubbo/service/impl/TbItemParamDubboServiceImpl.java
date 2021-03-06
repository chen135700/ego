package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.exception.DaoException;
import com.ego.mapper.TbItemCatMapper;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public List<TbItemParam> selectByPage(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(null);
        PageInfo<TbItemParam> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public long selectCount() {
        return tbItemParamMapper.countByExample(null);
    }

    @Override
    public TbItemParam selectByCatId(Long catId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(list!=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbItemParam tbItemParam) {

        return tbItemParamMapper.insert(tbItemParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(long[] ids) throws  DaoException{
        int index =0;
        for(long id :ids){
            index += tbItemParamMapper.deleteByPrimaryKey(id);
        }
        if(index == ids.length){
            return 1;
        }
        throw  new DaoException("删除失败");
    }
}
