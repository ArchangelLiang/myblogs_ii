package com.gwg.controller.admin;

import com.gwg.exceptions.BadRequestException;
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

    @PostMapping("deleteBlog")
    public String deleteBlog(Long id,ModelMap model){
        if (id == null) {
            throw new BadRequestException("进行删除操作时id不可以为空");
        }
        boolean b = this.blogService.deleteBlogById(id);
        if (!b) {
            throw new RuntimeException("操作异常");
        } else {
            PageResult<Blog> pageResult = this.blogService.listBlog(null, null, null, 1, 5);
            model.addAttribute("blogPageResult", pageResult);
            return "admin/admin_blog :: blogList";
        }
    }

    @ResponseBody
    @PostMapping("saveBlog")
    public RequestResult saveBlogOfJson(Blog blog, HttpSession session) {
        if (blog == null) {
            return new RequestResult("失败","");
        } else if (blog.getContent() == null || blog.getContent().trim().equals("") ||
                blog.getFirstPicture() == null || blog.getFirstPicture().trim().equals("") || blog.getPublished() == null) {
            return new RequestResult("失败","");
        } else {
            Date date = new Date();
            blog.setUpdateTime(date);
            blog.setUser((User) session.getAttribute("user"));
            if (blog.getId() != null) {
                this.blogService.updateBlog(blog);
            } else {
                blog.setCreateTime(date);
                this.blogService.saveBlog(blog);
            }
            return new RequestResult("成功","http://localhost:9990/admin/listBlog");
        }
    }

    @GetMapping("toPublish")
    public String toPublish(@RequestParam(value = "id",required = false) Long id,ModelMap model) {
        List<Type> types = this.typeService.listTypeAll();
        model.addAttribute("typeList", types);
        if (id != null) {
            Blog blog = this.blogService.getBlogById(id);
            model.addAttribute("updateBlog",blog);
        }
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
