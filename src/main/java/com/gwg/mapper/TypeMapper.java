package com.gwg.mapper;

import com.gwg.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TypeMapper {

    @Insert("insert into type (name) values(#{typeName})")
    boolean saveType(@Param("typeName") String typeName);

    @ResultMap("typeMap")
    @Select("select * from type where id = #{id}")
    Type getTypeById(@Param("id") int id);

    @Results(
            id = "typeMap",
            value = {@Result(property = "id", column = "id"),
                    @Result(
                            property = "blogList",column = "id",
                            many = @Many(select = "com.gwg.mapper.BlogMapper.getBlogByTypeId")
                    )
            }
    )
    @Select("select * from type")
    List<Type> getAll();

    @Update("update type set name = #{type.name} where id = #{type.id}")
    boolean updateType(@Param("type") Type type);

    @Delete("delete from type where id = #{id}")
    void deleteType(@Param("id") int id);

    @Select("select * from type limit #{start},#{size}")
    List<Type> getAllOfPage(@Param("start") int start, @Param("size") int size);

    @Select("select count(id) from type")
    int countType();

    @Select("select count(id) from type where name = #{typeName}")
    int getTypeCountByTyName(@Param("typeName") String typeName);
}
