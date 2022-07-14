package com.ego.item.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.CategoryNode;
import com.ego.item.pojo.ItemCategoryNav;
import com.ego.item.service.ItemService;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
   @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Override
    @Cacheable(cacheNames = "com.ego.item",key = "'showItemCat'")
    public ItemCategoryNav showItemCat() {
        System.out.println("执行方法");
        ItemCategoryNav itemCategoryNav = new ItemCategoryNav();
        itemCategoryNav.setData(getAllItemCat(0L));
        return itemCategoryNav;
    }

    private List<Object> getAllItemCat(Long parentId){
        List<TbItemCat> list = tbItemCatDubboService.selectByPid(parentId);
        List<Object> listResult = new ArrayList<>();
        // 一个cat对应一个菜单项目。前两层类型都是CategoryNode类型，第三层是String
        for(TbItemCat cat : list){
            // 说明是第一层或第二层。
            if(cat.getIsParent()){
                CategoryNode node = new CategoryNode();
                node.setU("/products/"+cat.getId()+".html");
                node.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
                node.setI(getAllItemCat(cat.getId()));
                listResult.add(node);
            }else{
                listResult.add("/products/"+cat.getId()+".html|"+cat.getName());
            }

        }
        return listResult;
    }
}
