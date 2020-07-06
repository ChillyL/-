package com.chilly.blog.service.impl;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Type;
import com.chilly.blog.mapper.BlogMapper;
import com.chilly.blog.mapper.TypeMapper;
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
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }
}
