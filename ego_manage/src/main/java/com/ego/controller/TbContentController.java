package com.ego.controller;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TbContentController {
    @Autowired
    private TbContentService tbContentService;

    @RequestMapping("/content/query/list")
    public EasyUIDatagrid showContent(Long categoryId,int page,int rows){
        return  tbContentService.showContent(categoryId,page,rows);
    }

    @RequestMapping("/content/save")
    public EgoResult insert(TbContent tbContent){
        return  tbContentService.insert(tbContent);
    }

    @RequestMapping("/rest/content/edit")
    public EgoResult update(TbContent tbContent){
        return  tbContentService.update(tbContent);
    }

    @RequestMapping("/content/delete")
    public EgoResult delete(long[] ids){
        return tbContentService.delete(ids);
    }

}
