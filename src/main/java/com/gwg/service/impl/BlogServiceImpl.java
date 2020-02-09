package com.gwg.service.impl;

import com.gwg.mapper.BlogMapper;
import com.gwg.pojo.Blog;
import com.gwg.service.BlogService;
import com.gwg.util.PageResult;
import com.gwg.util.PageResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog getBlogById(Long id) {
        return this.blogMapper.getBlogById(id);
    }

    @Override
    public PageResult<Blog> listBlog(String title, Integer typeId, Integer recommend,int pageNum, int pageSize) {
        int start = (pageNum - 1) * pageNum;
        List<Blog> blog_list = this.blogMapper.listBlog(title, typeId, recommend,start, pageSize);
        int total = this.blogMapper.countBlog(title, typeId,recommend);
        return PageResultBuilder.builder(blog_list, pageNum, pageSize, total);
    }

    @Override
    public boolean saveBlog(Blog blog) {
        return this.blogMapper.saveBlog(blog);
    }

    @Override
    public boolean deleteBlogById(Long id) {
        return this.blogMapper.deleteBlogById(id);
    }

    @Override
    public boolean updateBlog(Blog blog) {
        this.blogMapper.deleteBlogById(blog.getId());
        return this.blogMapper.saveBlog(blog);
    }
}
