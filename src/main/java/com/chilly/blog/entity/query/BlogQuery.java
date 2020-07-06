package com.chilly.blog.entity.query;

import com.chilly.blog.entity.Type;

import java.util.Date;

/**
 * @auther ChillyLin
 * @date 2020/6/29
 *
 * 管理博客列表实体类
 */
public class BlogQuery {

    private Long id;   //id
    private String title;  //标题
    private Date updateTime;  //更新时间
    private boolean recommend;  //推荐是否
    private Long type_id;
    private Type type;  //类型

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", recommend=" + recommend +
                ", type_id=" + type_id +
                ", type=" + type +
                '}';
    }
}
