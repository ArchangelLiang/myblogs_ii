package com.gwg.mapper;

import com.gwg.pojo.Blog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    @Select("select * form blog where id = #{id}")
    Blog getBlogById(@Param("id") Long id);

    @Results(
           value = {
                   @Result(id = true,column = "id",property = "id"),
                   @Result(column = "create_time",property = "createTime",jdbcType = JdbcType.TIMESTAMP),
                   @Result(column = "update_time",property = "updateTime",jdbcType = JdbcType.TIMESTAMP),
                   @Result(column = "type",property = "type",one = @One(select = "com.gwg.mapper.TypeMapper.getTypeById",fetchType = FetchType.LAZY))
           }
    )
    @SelectProvider(type = BlogSqlProvider.class, method = "getSearchSQL")
    List<Blog> listBlog(@Param("title") String title, @Param("typeId") Integer typeId,@Param("recommend") Integer recommend,@Param("start") int start, @Param("size") int size);

    //    @Insert("insert into blog (title, content, first_picture, flag, views, appreciation, share_statement, comment_able, recommend, create_time, update_time, type) VALUES (#{title}, #{content}, #{firstPicture}, #{flag.id}, #{views}, #{appreciation}, #{shareStatement}, #{commentAble}, #{recommend}, #{createTime}, #{updateTime}, #{type.id});")
    @InsertProvider(type = BlogSqlProvider.class, method = "getInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    boolean saveBlog(Blog blog);

    @Delete("delete from blog where id = #{id}")
    boolean deleteBlogById(Long id);

    @SelectProvider(type = BlogSqlProvider.class, method = "getBlogCountSQL")
    int countBlog(@Param("title") String title, @Param("typeId") Integer typeId,@Param("recommend") Integer recommend);

    class BlogSqlProvider {

        public String getBlogCountSQL(@Param("title") String title, @Param("typeId") Integer typeId,@Param("recommend") Integer recommend) {
            return new SQL() {{
                SELECT("count(id)");
                FROM("blog");
                if (title != null && !title.trim().equals("")) {
                    WHERE("title like %#{title}%");
                }
                if (typeId != null) {
                    WHERE("type = #{typeId}");
                }
                if (recommend != null){
                    WHERE("recommend = #{recommend}");
                }
            }}.toString();
        }

        public String getInsertSql(Blog blog) {
            return new SQL() {{
                INSERT_INTO("blog");
                if (blog.getId() != null) {
                    VALUES("id,title, content, first_picture, flag, views, appreciation, share_statement, comment_able, recommend, create_time, update_time, type",
                            "#{id},#{title}, #{content}, #{firstPicture}, #{flag}, #{views}, #{appreciation}, #{shareStatement}, #{commentAble}, #{recommend}, #{createTime}, #{updateTime}, #{type.id}");
                } else {
                    VALUES("title, content, first_picture, flag, views, appreciation, share_statement, comment_able, recommend, create_time, update_time, type",
                            "#{title}, #{content}, #{firstPicture}, #{flag}, #{views}, #{appreciation}, #{shareStatement}, #{commentAble}, #{recommend}, #{createTime}, #{updateTime}, #{type.id}");
                }
            }}.toString();
        }

        public String getSearchSQL(Map<String, Object> para) {
            return new SQL() {{
                SELECT("*");
                FROM("blog");
                if (para.get("title") != null && !String.valueOf(para.get("title")).equals("") ) {
                    WHERE("title like %" + para.get("title") + "%");
                }
                if (para.get("typeId") != null) {
                    WHERE("type = " + para.get("typeId"));
                }
                if (para.get("recommend") !=null && !String.valueOf(para.get("recommend")).trim().equals("")){
                    WHERE("recommend = #{recommend}");
                }
                LIMIT(String.valueOf(para.get("start")) + " , " + para.get("size"));
            }}.toString();
        }
    }
}
