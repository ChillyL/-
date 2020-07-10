package com.chilly.blog.service.impl;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Type;
import com.chilly.blog.mapper.AdminLoginMapper;
import com.chilly.blog.mapper.BlogMapper;
import com.chilly.blog.mapper.TypeMapper;
import com.chilly.blog.service.AdminLoginService;
import com.chilly.blog.service.BlogService;
import com.chilly.blog.service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/23
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private AdminLoginMapper adminLoginMapper;

    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Override
    public List<Type> listType() {
        return typeMapper.listType();
    }

    @Override
    public List<Type> listTypeAndBlog() {
        List<Type> typeList = this.listType();
        for (Type type : typeList){
            type.setBlogList(blogMapper.listBlogByType(type.getId()));
        }

        return typeList;
    }

    @Override
    public Type getTypeAndBlog(Long id) {
        Type type = this.getType(id);
        List<Blog> blogList = blogMapper.listBlogByType(type.getId());

        for (Blog blog : blogList){
            //补充blog对象的缺少的属性
            blog.setTags(blogMapper.findBlogTag(blog.getId()));
            blog.setType(typeMapper.getType(blog.getType_id()));
            blog.setUser(adminLoginMapper.getBlogAuthor(blog.getUser_id()));
        }

        type.setBlogList(blogList);

        return type;
    }

    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }
}
