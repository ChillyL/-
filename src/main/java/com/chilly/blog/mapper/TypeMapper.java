package com.chilly.blog.mapper;


import com.chilly.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {

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
     * @return 成功返回1
     */
    int saveType(Type type);

    /**
     * 分页查
     * @return
     */
    List<Type> listType();

    /**
     * 更改
     * @param type
     * @return 成功返回1
     */
    int updateType(Type type);

    /**
     * 删除
     * @param id
     */
    int deleteType(Long id);
}
