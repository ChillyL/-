package com.chilly.blog.controller.admin;

import com.chilly.blog.entity.Type;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chilly.blog.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/24
 */

@Controller
@RequestMapping("/admin")
public class TypeController {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TypeService typeService;

    //分页查询分类列表，引入MyBatis分页插件
    @GetMapping("/types")
    public String listTypes(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        //按照排序字段，倒序，排序
        String orderBy = "type_id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Type> list = typeService.listType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(list);

        logger.info("pageInfo:{}",pageInfo);
        //提交到页面
        model.addAttribute("pageInfo",pageInfo);

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String inputType(Model model){
        //为了可以将新建页面和编辑页面公用
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }

        int t = typeService.saveType(type);

        if (t == 1){
            //成功
            attributes.addFlashAttribute("message", "新增成功");
        }else {
            attributes.addFlashAttribute("message", "新增失败");
        }

        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //编辑修改分类
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int t = typeService.updateType(type);
        if (t == 1 ) {
            attributes.addFlashAttribute("message", "编辑成功");
        } else {
            attributes.addFlashAttribute("message", "编辑失败");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        int i = typeService.deleteType(id);
        if (i == 1){
            attributes.addFlashAttribute("message", "删除成功");
        }else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/types";
    }
}
