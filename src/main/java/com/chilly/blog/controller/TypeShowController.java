package com.chilly.blog.controller;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Type;
import com.chilly.blog.service.BlogService;
import com.chilly.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/7/10
 */
@Controller
public class TypeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id , @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum, Model model){

        List<Type> typeList = typeService.listTypeAndBlog();

        if (id == -1){
            id = typeList.get(0).getId();
        }

        List<Blog> blogList = typeService.getTypeAndBlog(id).getBlogList();

        PageHelper.startPage(pageNum,5);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("typeList",typeList);
        model.addAttribute("activeTypeId", id);  //判断选择的类型
        return "types";
    }
}
