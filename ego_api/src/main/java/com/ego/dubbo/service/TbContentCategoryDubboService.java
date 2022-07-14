package com.ego.dubbo.service;

import com.ego.exception.DaoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryDubboService {

    List<TbContentCategory> selectByPid(Long pid);

    int insert(TbContentCategory category)throws DaoException;

    int updateNameById(TbContentCategory tbContentCategory);

    int deleteById(Long id) throws  DaoException;
}
