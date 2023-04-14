package com.coffee.mapper;

import com.coffee.entity.User;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserMapper {

    List<User> findAll();

    User findById(Long id);

    void create(User user);

    void delete(Long id);

    void update(User user);

    User findByName(String name);
}
