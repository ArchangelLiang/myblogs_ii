package com.gwg.controller.admin;

import com.gwg.pojo.Blog;
import com.gwg.pojo.Type;
import com.gwg.pojo.User;
import com.gwg.service.BlogService;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import com.gwg.util.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @ResponseBody
    @PostMapping("saveBlog")
    public RequestResult saveBlogOfJson(Blog blog, HttpSession session) {
        if (blog == null) {
            return new RequestResult("失败","");
        } else if (blog.getContent() == null || blog.getContent().trim().equals("") ||
                blog.getFirstPicture() == null || blog.getFirstPicture().trim().equals("") || blog.getPublished() == null) {
            return new RequestResult("失败","");
        } else {
            blog.setAppreciation(blog.getAppreciation() == null ? 0 : 1);
            blog.setCommentAble(blog.getCommentAble() == null ? 0 : 1);
            blog.setRecommend(blog.getRecommend() == null ? 0 : 1);
            blog.setShareStatement(blog.getShareStatement() == null ? 0 : 1);
            Date date = new Date();
            blog.setCreateTime(date);
            blog.setUpdateTime(date);
            blog.setUser((User) session.getAttribute("user"));
            this.blogService.saveBlog(blog);
            return new RequestResult("成功","http://localhost:9990/admin/listBlog");
        }
    }

    @GetMapping("toPublish")
    public String toPublish(ModelMap model) {
        List<Type> types = this.typeService.listTypeAll();
        model.addAttribute("typeList", types);
        return "admin/admin_publish";
    }

    @GetMapping("listBlog")
    public String listBlog(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "typeId", required = false) Integer typeId,
                           @RequestParam(value = "recommend", required = false) Integer recommend,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, ModelMap model) {
        PageResult<Blog> pageResult = this.blogService.listBlog(title, typeId, recommend, pageNum, pageSize);
        model.addAttribute("blogPageResult", pageResult);
        List<Type> types = this.typeService.listTypeAll();
        model.addAttribute("typeList", types);
        return "admin/admin_blog";
    }

    @PostMapping("listTypeForJson")
    public String listTypeForJson(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "typeId", required = false) Integer typeId,
                                  @RequestParam(value = "recommend", required = false) Integer recommend,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, ModelMap model) {
        PageResult<Blog> pageResult = this.blogService.listBlog(title, typeId, recommend, pageNum, pageSize);
        model.addAttribute("blogPageResult", pageResult);
        return "admin/admin_blog :: blogList";
    }

}
