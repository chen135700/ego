package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDubboService;
import com.ego.exception.DaoException;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TbItemDubboServiceImpl implements TbItemDubboService
{
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public List<TbItem> selectByPage(int pageSize, int pageNumber) {
        PageHelper.startPage(pageSize,pageNumber);

        List<TbItem> list = tbItemMapper.selectByExample(null);
        PageInfo<TbItem> pi = new PageInfo<>(list);

        return pi.getList();
    }

    @Override
    public long selectCount() {
        return tbItemMapper.countByExample(null);
    }

    @Override
    @Transactional
    public int updateStatusByIds(long[] ids, int status)throws DaoException {
        int index =0;
        Date date = new Date();
        for(long id : ids){
            TbItem  tbItem = new TbItem();
            tbItem.setId(id);
            tbItem.setStatus((byte) status);
            tbItem.setUpdated(date);
            index+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        if(index==ids.length){
            return  1;
        }
         throw new DaoException("批量修改失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException{
        int index = tbItemMapper.insert(tbItem);
        if(index ==1){
            int index2 = tbItemDescMapper.insert(tbItemDesc);
            if(index2 ==1){
                int index3 = tbItemParamItemMapper.insert(tbItemParamItem);
                if(index3==1) {
                    return 1;
                }
            }
        }
        throw  new DaoException("新增失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws DaoException {
        int index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if(index ==1){
            int index2 = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if(index2 ==1){
                int index3 = tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
                if(index3 ==1){
                    return 1;
                }
            }
        }
        throw  new DaoException("修改失败");
    }
}
