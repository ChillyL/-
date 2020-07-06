package com.chilly.blog.service;

import com.chilly.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    /**
     * 查
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 按名字查
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 保存
     * @param type
     * @return
     */
    int saveType(Type type);

    /**
     * 分页查
     *
     * @return
     */
    List<Type> listType();

    /**
     * 分页查,匹配相应Blog
     *
     * @return
     */
    List<Type> listTypeAndBlog();

    /**
     * 更改
     * @param type
     * @return
     */
    int updateType(Type type);

    /**
     * 删除
     * @param id
     */
    int deleteType(Long id);
}
