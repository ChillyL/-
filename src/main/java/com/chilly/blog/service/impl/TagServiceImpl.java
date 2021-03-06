package com.chilly.blog.service.impl;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;
import com.chilly.blog.mapper.AdminLoginMapper;
import com.chilly.blog.mapper.BlogMapper;
import com.chilly.blog.mapper.TagMapper;
import com.chilly.blog.mapper.TypeMapper;
import com.chilly.blog.service.TagService;
import com.chilly.blog.service.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/6/23
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private AdminLoginMapper adminLoginMapper;

    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> listTagAndBlog() {
        List<Tag> tagList = this.listTag();
        for (Tag tag : tagList){
            tag.setBlogList(blogMapper.listBlogByTag(tag.getId()));
        }

        return tagList;
    }

    @Override
    public Tag getTagAndBlog(Long id) {
        Tag tag = this.getTag(id);
        List<Blog> blogList = blogMapper.listBlogByTag(tag.getId());

        for (Blog blog : blogList){
            //补充blog对象的缺少的属性
            blog.setTags(blogMapper.findBlogTag(blog.getId()));
            blog.setType(typeMapper.getType(blog.getType_id()));
            blog.setUser(adminLoginMapper.getBlogAuthor(blog.getUser_id()));
        }

        tag.setBlogList(blogList);

        return tag;
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }
}
