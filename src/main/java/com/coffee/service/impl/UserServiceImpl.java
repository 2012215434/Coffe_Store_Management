package com.coffee.service.impl;

import com.coffee.entity.User;
import com.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffee.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long... ids) {

        userRepository.deleteAllByIdInBatch(Arrays.asList(ids));

    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public long countByUserType(int userType) {
        return userRepository.countByUserType(userType);
    }
}
