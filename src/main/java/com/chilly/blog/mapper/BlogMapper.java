package com.chilly.blog.mapper;

import com.chilly.blog.entity.Blog;
import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.query.BlogQuery;
import com.chilly.blog.entity.query.RecommendBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {

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
     * 获取Blog
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 获取所有
     * @return
     */
    List<Blog> listBlog();

    /**
     * 新增
     * @param blog
     * @return
     */
    Long saveBlog(Blog blog);

    /**
     * 更新
     * @param blog
     * @return
     */
    int updateBlog(Blog blog);

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
     * 查找blog相关标签
     * @param blogId
     * @return
     */
    List<Tag> findBlogTag(Long blogId);

    /**
     * 修改的时候，直接将博客标签表中数据重置
     * @param blogId
     * @return
     */
    int deleteBlogTag(Long blogId);

    /**
     * 获取相关类型的所有博客
     * @param typeId
     * @return
     */
    List<Blog> listBlogByType(Long typeId);

    /**
     * 获取相关标签的所有博客
     * @param tagId
     * @return
     */
    List<Blog> listBlogByTag(Long tagId);

    /**
     * 获取文章标题模糊搜索
     * @param query
     * @return
     */
    List<Blog> listSearchBlog(@Param("query") String query);

    /**
     * 文章访问量增加
     * @param blogId
     * @return
     */
    int updateViews(Long blogId);

    /**
     * 查询出文章评论数量并更新
     * @param blogId
     * @return
     */
    int updateCommentCount(Long blogId);
}
