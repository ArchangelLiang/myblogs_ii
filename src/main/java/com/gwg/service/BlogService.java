package com.gwg.service;

import com.gwg.pojo.Blog;
import com.gwg.util.PageResult;

public interface BlogService {

    Blog getBlogById(Long id);

    PageResult<Blog> listBlog(String title, Integer typeId,Integer recommend,int pageNum,int pageSize);

    boolean saveBlog(Blog blog);

    boolean deleteBlogById(Long id);

    Blog updateBlog(Blog blog);

}
