package com.gwg.controller;

import com.gwg.pojo.Blog;
import com.gwg.pojo.Type;
import com.gwg.service.BlogService;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/toCategories")
    public String toPage(ModelMap model,@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum){

        List<Type> types = this.typeService.listTypeAll();
        model.addAttribute("types",types);
        PageResult<Blog> blogPageResult = this.blogService.listBlog(null, null, null, pageNum, 5);
        model.addAttribute("blogPageResult",blogPageResult);

        return "categories";
    }

    @GetMapping("/listBlog")
    public String listBlog(ModelMap model, @RequestParam(value = "typeId",required = false)Integer type,@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum){
        PageResult<Blog> blogPageResult = this.blogService.listBlog(null, type, null, pageNum, 5);
        model.addAttribute("blogPageResult",blogPageResult);
        Blog blog = null;
        List<Blog> result = blogPageResult.getResult();
        if (result.size() > 0) {
            blog = result.get(0);
        }
        String typeName;
        if (blog != null) {
            typeName = blog.getType().getName();
        } else {
            typeName = this.typeService.getType(type).getName();
        }
        model.addAttribute("typeName",typeName);
        return "categories::typeContentPage";
    }

}
