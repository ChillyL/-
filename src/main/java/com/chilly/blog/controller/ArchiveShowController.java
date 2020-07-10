package com.chilly.blog.controller;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @auther ChillyLin
 * @date 2020/7/10
 */

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model){
        Map<String , List<Blog>> blogMap = blogService.listBlogGroupDate();

        model.addAttribute("blogMap",blogMap);

        return "archives";
    }
}
