package com.chilly.blog.mapper;


import com.chilly.blog.entity.Tag;
import com.chilly.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

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
