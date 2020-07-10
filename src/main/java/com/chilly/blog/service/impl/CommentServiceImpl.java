package com.chilly.blog.service.impl;

import com.chilly.blog.entity.Comment;
import com.chilly.blog.mapper.BlogMapper;
import com.chilly.blog.mapper.CommentMapper;
import com.chilly.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther ChillyLin
 * @date 2020/7/8
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private BlogMapper blogMapper;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查出是父节点的评论,,
        List<Comment> comments = commentMapper.findByBlogIdParentIdNull(blogId);
        for (Comment comment : comments){
            Long id = comment.getId();
            String parentNickname = comment.getNickname();
            //查出该父节点的所有子节点
            List<Comment> childComments = commentMapper.findByBlogIdParentIdNotNull(blogId,id);

            combineChildren(blogId, childComments, parentNickname);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(Long blogId,List<Comment> childComments,String parentNickname){
        //查询出一级子评论
        if (childComments.size() > 0){
            for (Comment comment : childComments){
                String parentNickname1 = comment.getNickname();
                comment.setParentNickname(parentNickname);
                tempReplys.add(comment);
                Long childId = comment.getId();
                //根据子一级评论，找出其他所有二级评论
                recursively(blogId, childId, parentNickname1);
            }
        }
    }

    private void recursively(Long blogId, Long childId, String parentNickname1) {
//        根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentMapper.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    //    新增评论
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        if (comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
        }
        int comments = commentMapper.saveComment(comment);
//        文章评论计数
        blogMapper.updateCommentCount(comment.getBlogId());
        return comments;
    }

    //    删除评论
    @Override
    public void deleteComment(Comment comment,Long id) {
        commentMapper.deleteComment(id);
        blogMapper.updateCommentCount(comment.getBlogId());
    }
}
