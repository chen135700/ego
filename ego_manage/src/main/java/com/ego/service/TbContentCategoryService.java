package com.ego.service;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryService {

    List<EasyUITree> showContentCatory(Long pid);

    EgoResult insert(TbContentCategory tbContentCategory);

    EgoResult update(TbContentCategory tbContentCategory);

    EgoResult delete(Long id);
}
