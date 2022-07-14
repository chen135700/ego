package com.ego.service.impl;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.exception.DaoException;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemService;
import com.ego.utils.IDUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class TbIemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Override
    public EasyUIDatagrid showItem(int page, int rows) {
        List<TbItem> list = tbItemDubboService.selectByPage(page, rows);
        long total =tbItemDubboService.selectCount();

        return new EasyUIDatagrid(list,total);

    }

    @Override
    public EgoResult updateStatus(long[] ids, int status) {
        try {
            int index = tbItemDubboService.updateStatusByIds(ids, status);
            if (index == 1) {
                return EgoResult.ok();
            }
        }catch (DaoException e){
            e.printStackTrace();
        }

        return EgoResult.error("操作失败");
    }

    @Override
    public EgoResult insert(TbItem item, String desc,String itemParams) {
        Date date = new Date();
        long id = IDUtils.genItemId();

        item.setCreated(date);
        item.setUpdated(date);
        item.setId(id);
        item.setStatus((byte) 1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setCreated(date);

        //商品规格参数
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(IDUtils.genItemId());
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);

        try{
            int index =tbItemDubboService.insert(item,tbItemDesc,tbItemParamItem);
            if(index == 1){
                return EgoResult.ok();
            }
        }catch (DaoException e){
            e.printStackTrace();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult update(TbItem item, String desc,String itemParams,long itemParamId) {
        Date date = new Date();
        item.setUpdated(date);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(item.getId());

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(date);

        try {
            int index = tbItemDubboService.update(item,tbItemDesc,tbItemParamItem);
            if(index==1){
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("修改失败");
    }
}
