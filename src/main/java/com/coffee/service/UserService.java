package com.coffee.service;

import com.coffee.entity.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    Optional<User> findByName(String name);

    long countByUserType(int userType);
}
