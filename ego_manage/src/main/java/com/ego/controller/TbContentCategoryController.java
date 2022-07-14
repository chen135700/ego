package com.ego.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TbContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @RequestMapping("/content/category/list")
    public List<EasyUITree> showContentCategory(@RequestParam(defaultValue = "0") Long id){
        return  tbContentCategoryService.showContentCatory(id);
    }

    @RequestMapping("/content/category/create")
    public EgoResult insert(TbContentCategory tbContentCategory){
        return tbContentCategoryService.insert(tbContentCategory);
    }

    @RequestMapping("/content/category/update")
    public EgoResult update(TbContentCategory tbContentCategory){
        return tbContentCategoryService.update(tbContentCategory);
    }

    @RequestMapping("/content/category/delete")
    public EgoResult delete(Long id ){
        return tbContentCategoryService.delete(id);
    }

}
