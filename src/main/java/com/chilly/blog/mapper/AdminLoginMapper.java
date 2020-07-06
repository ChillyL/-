package com.chilly.blog.mapper;


import com.chilly.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface AdminLoginMapper {

    /**
     * 获取用户
     * @param username
     * @param password
     * @return
     */
    User adminLogin(@Param("username")String username,@Param("password")String password);

    /**
     * 获取作者
     * @param id
     * @return
     */
    User getBlogAuthor(Long id);
}
