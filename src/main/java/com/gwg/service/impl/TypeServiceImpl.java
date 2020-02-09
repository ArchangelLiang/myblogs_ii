package com.gwg.service.impl;

import com.gwg.mapper.TypeMapper;
import com.gwg.pojo.Type;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import com.gwg.util.PageResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public boolean saveType(String typeName) {
        if (typeName == null || typeName.trim().equals("")) {
            return false;
        }
        if (this.typeMapper.getTypeCountByTyName(typeName) > 0) {
            return false;
        }
        return this.typeMapper.saveType(typeName);
    }

    @Override
    public Type getType(int id) {
        return this.typeMapper.getTypeById(id);
    }

    @Override
    public PageResult<Type> listType(int pageNum,int pageSize) {
        int start = (pageNum - 1) * pageSize;
        List<Type> typeList = this.typeMapper.getAllOfPage(start,pageSize);
        int countType = this.typeMapper.countType();
        return PageResultBuilder.builder(typeList,pageNum,pageSize,countType);
    }

    @Transactional
    @Override
    public boolean updateType(Type type) {
        if (type == null || type.getId() == null || type.getName() == null){
            return false;
        }
        return this.typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(int id) {
        this.typeMapper.deleteType(id);
    }

    @Override
    public List<Type> listTypeAll() {
        return this.typeMapper.getAll();
    }
}
