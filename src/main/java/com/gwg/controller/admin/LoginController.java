package com.gwg.controller.admin;

import com.gwg.pojo.User;
import com.gwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(String userName, String password, HttpSession session, RedirectAttributes redirectAttributes){
        User user = this.userService.checkUser(userName, password);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMsg","用户名或密码错误");
            return "redirect:/admin";
        }
        //安全起见，不保存用户密码
        user.setPassword(null);
        session.setAttribute("user",user);
        return "admin/admin";
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
