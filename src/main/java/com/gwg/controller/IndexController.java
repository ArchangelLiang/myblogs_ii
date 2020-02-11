package com.gwg.controller;

import com.gwg.pojo.Blog;
import com.gwg.pojo.Type;
import com.gwg.service.BlogService;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import com.gwg.util.RecommendBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("listBlog")
    public String listBlog(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "typeId", required = false) Integer typeId,
                           @RequestParam(value = "recommend", required = false) Integer recommend,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, ModelMap model){
        PageResult<Blog> pageResult = this.blogService.listBlog(title, null, null, pageNum, pageSize);
        model.addAttribute("blogPageResult", pageResult);
        return "index::primary";
    }

    @GetMapping("index")
    public String listBlog(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, ModelMap model) {
        PageResult<Blog> pageResult = this.blogService.listBlog(title, null, null, pageNum, pageSize);
        model.addAttribute("blogPageResult", pageResult);
        if (title == null || title.trim().equals("")) {
            List<Type> typeList = this.typeService.listTypeAll();
            if (typeList != null && typeList.size() > 0) {
                typeList.sort((o1, o2) -> -(o1.getBlogList().size() - o2.getBlogList().size()));
                ArrayList<Type> types = new ArrayList<>();
                for (int i = 0; i < Math.min(typeList.size(), 6); i++) {
                    types.add(typeList.get(i));
                }
                model.addAttribute("TopTypes", types);
            }
            List<RecommendBlog> recommendBlogTitleList = this.blogService.recommendBlogTitle();
            model.addAttribute("recommendBlogTitleList", recommendBlogTitleList);
            return "index";
        }
        return "search";
    }

}
