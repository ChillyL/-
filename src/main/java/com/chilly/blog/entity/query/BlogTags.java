package com.chilly.blog.entity.query;

/**
 * @auther ChillyLin
 * @date 2020/6/30
 */
public class BlogTags {

    private Long id;
    private Long tagId;
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
