package com.gwg.mapper;

import com.gwg.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Insert("INSERT INTO comment (nick_name, email, content, create_time, parent_comment_id, blog_id) " +
            "values (#{nickName},#{email},#{content},#{createTime},#{parentCommentId},#{blog.id})")
    void saveComment(Comment comment);

    @Select("select * from comment where blog_id = #{blogId}")
    List<Comment> listComment(Long blogId);
}
