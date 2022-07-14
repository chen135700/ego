package com.ego.dubbo.service;


import com.ego.exception.DaoException;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

public interface TbItemDubboService {
        List<TbItem> selectByPage(int pageSize,int pageNumber);

        long selectCount();

        int updateStatusByIds(long[] ids,int status) throws DaoException;

        int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException;

        int update(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws  DaoException;


}
