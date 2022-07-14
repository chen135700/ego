package com.ego.service;

import com.ego.commons.pojo.EasyUITree;

import java.util.List;

public interface TbItemCatService {

    List<EasyUITree> showTree(Long pid);
}
