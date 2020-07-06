package com.chilly.blog.entity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/19
 */
public class Type {

    private Long id;

    @NotNull(message = "分类名称不能为空")  //限制必须不为null，加入@Valid检验
    private String name;

    private List<Blog> blogList = new ArrayList<Blog>();  //加入相关博客列表

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
