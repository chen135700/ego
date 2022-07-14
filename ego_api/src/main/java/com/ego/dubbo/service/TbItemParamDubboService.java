package com.ego.dubbo.service;

import com.ego.exception.DaoException;
import com.ego.pojo.TbItemParam;

import java.util.List;

public interface TbItemParamDubboService {


    List<TbItemParam> selectByPage(int pageNumber,int pageSize);

    long selectCount();

    TbItemParam selectByCatId(Long catId);

    int insert(TbItemParam tbItemParam);

    int delete(long[] ids) throws DaoException;
}
