package com.chilly.blog.mapper;


import com.chilly.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 根据创建时间倒序来排
     * @param blogId
     * @return
     */
    List<Comment> findByBlogIdParentIdNull(@Param("blogId") Long blogId);

    /**
     * 查询一级回复
     * @param blogId
     * @param id
     * @return
     */
    List<Comment> findByBlogIdParentIdNotNull(@Param("blogId") Long blogId, @Param("id") Long id);

    /**
     * 查询二级回复
     * @param blogId
     * @param childId
     * @return
     */
    List<Comment> findByBlogIdAndReplayId(@Param("blogId") Long blogId,@Param("childId") Long childId);

    /**
     * 添加一个评论
     * @param comment
     * @return
     */
    int saveComment(Comment comment);

    /**
     * 删除评论
     * @param id
     */
    void deleteComment(Long id);
}
