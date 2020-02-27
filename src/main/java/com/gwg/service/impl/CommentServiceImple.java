package com.gwg.service.impl;

import com.gwg.mapper.CommentMapper;
import com.gwg.pojo.Comment;
import com.gwg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImple implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void saveComment(Comment comment) {
        this.commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> listComment(Long blogId) {
        return this.commentMapper.listComment(blogId);
    }
}
