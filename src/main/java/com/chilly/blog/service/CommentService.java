package com.chilly.blog.service;

import com.chilly.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);

    void deleteComment(Comment comment, Long id);
}
