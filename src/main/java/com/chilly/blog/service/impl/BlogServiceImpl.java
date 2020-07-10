package com.chilly.blog.service.impl;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.query.BlogQuery;
import com.chilly.blog.entity.query.RecommendBlog;
import com.chilly.blog.exception.NotFoundException;
import com.chilly.blog.mapper.AdminLoginMapper;
import com.chilly.blog.mapper.BlogMapper;
import com.chilly.blog.mapper.TypeMapper;
import com.chilly.blog.service.BlogService;
import com.chilly.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @auther ChillyLin
 * @date 2020/6/27
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private AdminLoginMapper adminLoginMapper;

    @Override
    public List<BlogQuery> listBlogQuery() {
        return blogMapper.listBlogQuery();
    }

    @Override
    public List<BlogQuery> listBlogByTitleOrType(BlogQuery blogQuery) {
        return blogMapper.listBlogByTitleOrType(blogQuery);
    }

    @Override
    public List<RecommendBlog> listBlogByRecommend() {
        return blogMapper.listBlogByRecommend();
    }

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Override
    public Blog getAndConvertBlog(Long id) {

        Blog b = blogMapper.getBlog(id);
        if (b == null){
            throw new NotFoundException("该博客不存在");
        }
        //防止操作到数据库，更改了数据库中content
        Blog blog = new Blog();
        //复制
        BeanUtils.copyProperties(b,blog);

        //将content转HTML
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //补充blog对象的缺少的属性
        blog.setTags(blogMapper.findBlogTag(blog.getId()));
        blog.setType(typeMapper.getType(blog.getType_id()));
        blog.setUser(adminLoginMapper.getBlogAuthor(blog.getUser_id()));

        //文章访问量自增
        blogMapper.updateViews(blog.getId());

        return blog;
    }

    @Override
    public List<Blog> listBlog() {
        return indexListBlog();
    }

    public List<Blog> indexListBlog(){
        List<Blog> blogList = blogMapper.listBlog();
        for (Blog blog : blogList){
            //补充blog对象的缺少的属性
            blog.setTags(blogMapper.findBlogTag(blog.getId()));
            blog.setType(typeMapper.getType(blog.getType_id()));
            blog.setUser(adminLoginMapper.getBlogAuthor(blog.getUser_id()));
        }
        return blogList;
    }

    @Override
    public int saveBlog(Blog blog,String tagIds) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setComment_count(0);

        blogMapper.saveBlog(blog); //通过selectKey返回最近插入的id到blog对象的id

        Long blogId = blog.getId();
        //保存博客相关的标签
        int i = 0;
        List<Long> list = convertToList(tagIds);
        for (Long tagId : list){
            i = blogMapper.saveBlogTag(blogId,tagId);
        }
        return i;
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public int updateBlog(Blog blog,String tagIds) {
        blog.setUpdateTime(new Date());

        blogMapper.updateBlog(blog);

        Long blogId = blog.getId();
        //保存博客相关的标签
        int i = 0;
        List<Long> list = convertToList(tagIds);
        for (Long tagId : list){
            i = blogMapper.saveBlogTag(blogId,tagId);
        }
        return i;
    }

    @Override
    public List<Tag> updateBlogTags(Long blogId) {
        List<Tag> tagList = blogMapper.findBlogTag(blogId);
        System.out.println(tagList);
        blogMapper.deleteBlogTag(blogId);
        return tagList;
    }

    @Override
    public int deleteBlog(Long id) {
        //先删除标签表相应的内容
        blogMapper.deleteBlogTag(id);

        return blogMapper.deleteBlog(id);
    }

    @Override
    public int saveBlogTag(Long blogId, Long tagId) {
        return blogMapper.saveBlogTag(blogId,tagId);
    }

    @Override
    public List<Blog> listSearchBlog(String query) {

        List<Blog> blogList = blogMapper.listSearchBlog(query);
        for (Blog blog : blogList){
            //补充blog对象的缺少的属性
            blog.setTags(blogMapper.findBlogTag(blog.getId()));
            blog.setType(typeMapper.getType(blog.getType_id()));
            blog.setUser(adminLoginMapper.getBlogAuthor(blog.getUser_id()));
        }
        return blogList;
    }

    @Override
    public Map<String, List<Blog>> listBlogGroupDate() {
        List<String> dateList = blogMapper.listDateAboutBlog();

        Map<String, List<Blog>> map = new HashMap<>();
        for (String date : dateList){
            map.put(date,blogMapper.listBlogByDate(date));
        }

        return map;
    }
}
