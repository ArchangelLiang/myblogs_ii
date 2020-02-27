package com.gwg.controller;

import com.gwg.pojo.Blog;
import com.gwg.pojo.Comment;
import com.gwg.pojo.Type;
import com.gwg.service.BlogService;
import com.gwg.service.CommentService;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import com.gwg.util.RecommendBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CommentService commentService;

    @PostMapping("saveComment")
    public String saveComment(@RequestParam("blogId") Long blogId,@RequestParam("commentContent") String commentContent,
                              @RequestParam("nickName") String nickName,@RequestParam("commentEmail") String commentEmail,
                              @RequestParam(value = "parentCommentId",required = false) Integer parentCommentId,ModelMap model){
        Comment comment = new Comment();
        Blog blog = new Blog();
        blog.setId(blogId);
        comment.setBlog(blog);
        comment.setContent(commentContent);
        comment.setCreateTime(new Date());
        comment.setNickName(nickName);
        comment.setEmail(commentEmail);
        comment.setParentCommentId(parentCommentId);

        this.commentService.saveComment(comment);

        List<Comment> comments = this.commentService.listComment(blogId);

        comments.sort((c1,c2) -> -(c1.getId() - c2.getId()));

        model.addAttribute("comments",comments);

        return "blog::comment-fragment";
    }

    @GetMapping("/blog/{id}")
    public String getBlogById(@PathVariable("id") Long id, ModelMap model){
        Blog blog = this.blogService.getBlogAndConvert(id);
        model.addAttribute("blog",blog);

        List<Comment> comments = this.commentService.listComment(id);

        comments.sort((c1,c2) -> -(c1.getId() - c2.getId()));

        model.addAttribute("comments",comments);

        return "blog";
    }

    @GetMapping("listBlog")
    public String listBlog(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, ModelMap model){
        PageResult<Blog> pageResult = this.blogService.listBlog(null, null, null, pageNum, pageSize);
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
        model.addAttribute("query",title);
        return "search";
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/index";
    }

}
