package com.gwg.service;

import com.gwg.pojo.Type;
import com.gwg.util.PageResult;

import java.util.List;

public interface TypeService {

    boolean saveType(String typeName);

    Type getType(int id);

    PageResult<Type> listType(int pageNum,int pageSize);

    boolean updateType(Type type);

    void deleteType(int id);

    List<Type> listTypeAll();
}
