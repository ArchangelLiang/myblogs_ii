package com.gwg.service;

import com.gwg.pojo.Comment;

import java.util.List;

public interface CommentService {

    void saveComment(Comment comment);

    List<Comment> listComment(Long blogId);
}
