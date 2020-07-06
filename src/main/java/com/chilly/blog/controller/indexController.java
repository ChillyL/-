package com.chilly.blog.controller;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;
import com.chilly.blog.entity.query.RecommendBlog;
import com.chilly.blog.service.BlogService;
import com.chilly.blog.service.TagService;
import com.chilly.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @auther ChillyLin
 * @date 2020/6/17
 */
@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, RedirectAttributes attributes){
        PageHelper.startPage(pageNum,10);
        List<Blog> blogList = blogService.listBlog();
        List<Type> typeList = typeService.listTypeAndBlog();
        List<Tag> tagList = tagService.listTagAndBlog();
        List<RecommendBlog> recommendBlogList = blogService.listBlogByRecommend();

        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("typeList",typeList);
        model.addAttribute("tagList",tagList);
        model.addAttribute("recommendBlogList",recommendBlogList);

        return "index";
    }

    @PostMapping("/search")
    public String search(String query, Model model,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {

        PageHelper.startPage(pageNum, 10);

        List<Blog> blogList = new ArrayList<>();

        if (query.equals("") || query == null){

        }else {
            blogList = blogService.listSearchBlog(query);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

}
