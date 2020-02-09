package com.gwg.controller.admin;

import com.gwg.exceptions.BadRequestException;
import com.gwg.exceptions.NotFoundException;
import com.gwg.pojo.Type;
import com.gwg.service.TypeService;
import com.gwg.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;


    @GetMapping("/deleteType")
    public String deleteType(String id){
        if (id == null || id.trim().equals("")) {
            throw new BadRequestException("删除类别时，必须携带id参数");
        }
        this.typeService.deleteType(Integer.parseInt(id));
        return "redirect:/admin/listType";
    }

    @PostMapping("/saveType")
    public String saveType(@RequestParam(value = "id",required = false) String id, @RequestParam(value = "typeName") String typeName, RedirectAttributes redirectAttributes){
        if (typeName == null || typeName.trim().equals("")) {
            throw new BadRequestException("保存参数时，TypeName必须不为空");
        }
        if (id != null && !id.trim().equals("")) {
            this.typeService.updateType(new Type(Integer.parseInt(id),typeName));
        } else {
            boolean b = this.typeService.saveType(typeName);
            if (!b){
                redirectAttributes.addFlashAttribute("message","操作失败");
                redirectAttributes.addFlashAttribute("errorValue",typeName);
                return "redirect:/admin/toType";
            }
        }
        return "redirect:/admin/listType";
    }

    @GetMapping("/listType")
    public String listType(@RequestParam(required = false,defaultValue = "1") int pageNum,@RequestParam(required = false,defaultValue = "5") int pageSize, ModelMap model){
        if (pageNum <= 0) {
            throw new NotFoundException("非法参数");
        }
        PageResult<Type> pageResult = this.typeService.listType(pageNum, pageSize);
        model.addAttribute("pageResult",pageResult);
        return "admin/admin_categories";
    }

    @GetMapping("/toType")
    public String toTypePage(@RequestParam(value = "id",required = false) String id, ModelMap model){
        if (id != null && !id.trim().equals("")) {
            Type type = this.typeService.getType(Integer.parseInt(id));
            model.addAttribute("updateType",type);
        }
        return "admin/admin_add_categories";
    }
}
