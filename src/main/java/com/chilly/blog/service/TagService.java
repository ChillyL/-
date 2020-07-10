package com.chilly.blog.service;

import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;

import java.util.List;

public interface TagService {

    /**
     * 查
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 按名字查
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 保存
     * @param tag
     * @return 成功返回1
     */
    int saveTag(Tag tag);

    /**
     * 分页查
     * @return
     */
    List<Tag> listTag();

    /**
     * 分页查,匹配相应Blog
     *
     * @return
     */
    List<Tag> listTagAndBlog();

    /**
     * 单个查,匹配相应Blog
     *
     * @return
     */
    Tag getTagAndBlog(Long id);

    /**
     * 更改
     * @param tag
     * @return 成功返回1
     */
    int updateTag(Tag tag);

    /**
     * 删除
     * @param id
     */
    int deleteTag(Long id);
}
