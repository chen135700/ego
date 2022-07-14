package com.ego.dubbo.service;

import com.ego.exception.DaoException;
import com.ego.pojo.TbContent;

import java.util.List;

public interface TbContentDubboService {
    List<TbContent> selectByPage(Long categoryId,int pageNumber,int pageSize);
    long selectCountByCategoryid(Long categoryId);

    int insert(TbContent tbContent);
    int update(TbContent tbContent);
    int deleteByIds(long[] ids)  throws DaoException;
    List<TbContent> selectAllByCategoryid(Long categoryId);
}
