package com.chilly.blog.controller.admin;

import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;
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

import javax.validation.Valid;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/24
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagService tagService;

    //分页查询分类列表，引入MyBatis分页插件
    @GetMapping("/tags")
    public String listTags(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        //按照排序字段，倒序，排序
        String orderBy = "tag_id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Tag> list = tagService.listTag();
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(list);

        logger.info("pageInfo:{}",pageInfo);
        //提交到页面
        model.addAttribute("pageInfo",pageInfo);

        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String inputTag(Model model){
        //为了可以将新建页面和编辑页面公用
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }

        int t = tagService.saveTag(tag);

        if (t == 1){
            //成功
            attributes.addFlashAttribute("message", "新增成功");
        }else {
            attributes.addFlashAttribute("message", "新增失败");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    //编辑修改分类
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }
        int t = tagService.updateTag(tag);
        if (t == 1 ) {
            attributes.addFlashAttribute("message", "编辑成功");
        } else {
            attributes.addFlashAttribute("message", "编辑失败");
        }
        return "redirect:/admin/tags";
    }

    //删除分类
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        int i = tagService.deleteTag(id);
        if (i == 1){
            attributes.addFlashAttribute("message", "删除成功");
        }else {
            attributes.addFlashAttribute("message", "删除失败");
        }

        return "redirect:/admin/tags";
    }
}
