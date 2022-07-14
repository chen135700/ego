package com.ego.controller;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TbItemParamController {
    @Autowired
    private TbItemParamService tbItemParamService;


    @RequestMapping("/item/param/list")
    public EasyUIDatagrid showItemparam(int page, int rows) {
        return tbItemParamService.showItemParam(page, rows);
    }

    @RequestMapping("/item/param/query/itemcatid/{id}")
    public EgoResult showItemParamByCatId(@PathVariable Long id){
        return tbItemParamService.showItemParamByCatId(id);
    }

    @RequestMapping("/item/param/save/{catId}")
    public EgoResult insert(TbItemParam tbItemParam,@PathVariable Long catId){
        tbItemParam.setItemCatId(catId);
        return tbItemParamService.insert(tbItemParam);
    }

    @RequestMapping("/item/param/delete")
    public EgoResult delete(long[] ids){
        return tbItemParamService.delete(ids);

    }
}
