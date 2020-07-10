package com.chilly.blog.service;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.query.BlogQuery;
import com.chilly.blog.entity.query.RecommendBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**
     * 获取Blog
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 从数据库获取Blog,并将其内容中的md,转成HTML，用于前端显示
     * @param id
     * @return
     */
    Blog getAndConvertBlog(Long id);

    /**
     * 获取所有
     * @return
     */
    List<Blog> listBlog();


    /**
     * 查询文章管理列表
     * @return
     */
    List<BlogQuery> listBlogQuery();

    /**
     * 分页查，条件查,通过分类或者名称
     * @param blogQuery
     * @return
     */
    List<BlogQuery> listBlogByTitleOrType(BlogQuery blogQuery);

    /**
     * 推荐的文章按照时间取最新5个
     * @return
     */
    List<RecommendBlog> listBlogByRecommend();

    /**
     * 新增
     * @param blog
     * @return
     */
    int saveBlog(Blog blog,String tagIds);

    /**
     * 更新
     * @param blog
     * @return
     */
    int updateBlog(Blog blog,String tagIds);

    /**
     * 更新标签
     * @return
     */
    List<Tag> updateBlogTags(Long blogId);

    /**
     * 删除
     * @param id
     */
    int deleteBlog(Long id);

    /**
     * 保存blog相关标签
     * @param blogId
     * @param tagId
     * @return
     */
    int saveBlogTag(Long blogId,Long tagId);

    /**
     * 获取文章标题模糊搜索
     * @param query
     * @return
     */
    List<Blog> listSearchBlog(String query);

    /**
     * 按照年月
     * @return
     */
    Map<String,List<Blog>> listBlogGroupDate();
}
