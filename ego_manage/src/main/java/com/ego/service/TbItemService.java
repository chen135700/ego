package com.ego.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;

public interface TbItemService {

    EasyUIDatagrid showItem(int page,int rows);

    EgoResult updateStatus(long[] ids,int status);

    EgoResult insert(TbItem item , String desc,String itemParams);

    EgoResult update(TbItem item,String desc,String itemParams,long itemParamId);


}
