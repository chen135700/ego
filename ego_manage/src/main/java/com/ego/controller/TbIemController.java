package com.ego.controller;


import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;

import com.ego.pojo.TbItem;
import com.ego.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbIemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDatagrid showItem(int page,int rows){
        return tbItemService.showItem(page,rows);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult delete(long[] ids){
        return tbItemService.updateStatus(ids,3);
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(long[] ids){
        return tbItemService.updateStatus(ids,1);
    }

    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult instock(long[] ids){
        return tbItemService.updateStatus(ids,2);
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem item, String desc,String itemParams){
        return  tbItemService.insert(item,desc,itemParams);
    }

    @RequestMapping("rest/item/update")
    @ResponseBody
    public EgoResult update(TbItem item,String desc,String itemParams,long itemParamId){
        return  tbItemService.update(item,desc,itemParams,itemParamId);
    }


}
