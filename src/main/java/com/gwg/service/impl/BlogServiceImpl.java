package com.gwg.service.impl;

import com.gwg.mapper.BlogMapper;
import com.gwg.pojo.Blog;
import com.gwg.service.BlogService;
import com.gwg.util.MarkdownUtils;
import com.gwg.util.PageResult;
import com.gwg.util.PageResultBuilder;
import com.gwg.util.RecommendBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "blog")
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Cacheable(key = "#id")
    @Override
    public Blog getBlogById(Long id) {
        return this.blogMapper.getBlogById(id);
    }

    @Override
    public PageResult<Blog> listBlog(String title, Integer typeId, Integer recommend,int pageNum, int pageSize) {
        if (title != null && !title.trim().equals("")){
            title = "%"+title+"%";
        }
        int start = (pageNum - 1) * pageSize;
        List<Blog> blog_list = this.blogMapper.listBlog(title, typeId, recommend,start, pageSize);
        int total = this.blogMapper.countBlog(title, typeId,recommend);
        return PageResultBuilder.builder(blog_list, pageNum, pageSize, total);
    }

    @Override
    public boolean saveBlog(Blog blog) {
        return this.blogMapper.saveBlog(blog);
    }

    @CacheEvict(key = "#id")
    @Override
    public boolean deleteBlogById(Long id) {
        return this.blogMapper.deleteBlogById(id);
    }


    @CachePut(key = "#blog.id")
    @Override
    public Blog updateBlog(Blog blog) {
        this.blogMapper.deleteBlogById(blog.getId());
        this.blogMapper.saveBlog(blog);
        return blog;
    }

    @Override
    public List<RecommendBlog> recommendBlogTitle() {
        return this.blogMapper.getRecommendBlogTitle();
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = this.blogMapper.getBlogById(id);
        blog.setContent(MarkdownUtils.markdownToHtml(blog.getContent()));
        return blog;
    }

}
