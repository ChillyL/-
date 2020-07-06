package com.chilly.blog.controller.admin;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;
import com.chilly.blog.entity.User;
import com.chilly.blog.entity.query.BlogQuery;
import com.chilly.blog.service.BlogService;
import com.chilly.blog.service.TagService;
import com.chilly.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/23
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //博客列表
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        PageHelper.startPage(pageNum,10);
        List<BlogQuery> list = blogService.listBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(list);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("pageInfo",pageInfo);

        return "admin/blogs";
    }

    //跳转博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model) {

        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());

        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        //type
        blog.setType(typeService.getType(blog.getType().getId()));
        //type_id
        blog.setType_id(blog.getType().getId());
        //user_id
        blog.setUser_id(blog.getUser().getId());

        //保存
        int b = blogService.saveBlog(blog,blog.getTagIds());

        if (b == 1){
            attributes.addFlashAttribute("message", "新增成功");
        }else {
            attributes.addFlashAttribute("message", "新增失败");
        }

        return "redirect:/admin/blogs";
    }

    //删
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int i = blogService.deleteBlog(id);
        if (i == 1){
            attributes.addFlashAttribute("message", "删除成功");
        }else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/blogs";
    }

    //    跳转编辑修改文章页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        Blog blog = blogService.getBlog(id);
        //type
        blog.setType(typeService.getType(blog.getType_id()));
        //相关博客对应的tag
        blog.setTags(blogService.updateBlogTags(id));
        logger.info("blog_tag : {}",blog.getTags());
        blog.setTagIds(tagsToIds(blog.getTags()));

        List<Type> typeList = typeService.listType();
        List<Tag> tagList = tagService.listTag();

        model.addAttribute("blog",blog);
        model.addAttribute("types",typeList);
        model.addAttribute("tags",tagList);

        return "admin/blogs-input";
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {

        StringBuffer ids = new StringBuffer();
        boolean flag = false;
        for (Tag tag : tags) {
            if (flag) {
                ids.append(",");
            } else {
                flag = true;
            }
            ids.append(tag.getId());
        }
        return ids.toString();

    }

    //编辑修改文章
    @PostMapping("/blogs/{id}")
    public String editPost(Blog blog,RedirectAttributes attributes){

        int i = blogService.updateBlog(blog,blog.getTagIds());

        if (i == 1){
            attributes.addFlashAttribute("message", "修改成功");
        }else {
            attributes.addFlashAttribute("message", "修改失败");
        }

        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String sreach(BlogQuery blogQuery,Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        PageHelper.startPage(pageNum,10);

        List<BlogQuery> blogQueries = blogService.listBlogByTitleOrType(blogQuery);
        logger.info("listBlogByTitleOrType : {}",blogQueries);
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(blogQueries);
        model.addAttribute("pageInfo",pageInfo);

        //动态局部刷新
        //返回到admin/blogs.html中的blogList片段,即  th:fragment="blogList" 所在
        return "admin/blogs :: blogList";
    }

}
