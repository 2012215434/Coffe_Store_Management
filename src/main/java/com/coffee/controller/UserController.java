package com.coffee.controller;

import com.coffee.entity.Result;
import com.coffee.entity.User;
import com.coffee.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

//    @RequestMapping("/user")
//    public Result getUser(HttpServletRequest request) {
//        // 获取登录信息，从session中的用户id去数据库中做查找
//        User user = (User) request.getSession().getAttribute("user");
//        if(user !=null) {
//            User userFromDb = userService.findById(user.getId()).get();
//            return new Result(true, userFromDb);
//        }else {
//            return new Result(false, null);
//        }
//    }

    @RequestMapping("/employee/users")
    public List<User> findAll() {
        return userService.findAll();
    }


    @RequestMapping(value = "/public/users", method = RequestMethod.POST)
    public Result registerUser(@RequestBody User user) {
        validate(user);
        // 对于员工账号只能注册一次
        if (1 == user.getUserType()) {
            if (userService.countByUserType(1) > 0) {
                return new Result(false, "already exist employee account");
            }
        }
        //同一个email只能注册一次
        if (userService.findByName(user.getEmail()).isPresent()) {
            return new Result(false, "email already registered");
        }

        try {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.create(user);
            return new Result(true, "create user success, please login!");
        } catch (Exception e) {
            logger.error("exception in register user", e);
            return new Result(false, e.getMessage());
        }
    }

    private void validate(User user) {
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new IllegalArgumentException("email is empty");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new IllegalArgumentException("password is empty");
        }
        if (StringUtils.isEmpty(user.getFirstName())) {
            throw new IllegalArgumentException("firstName is empty");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new IllegalArgumentException("phone is empty");
        }
        if (user.getUserType() == null) {
            throw new IllegalArgumentException("user Type is null");
        }
    }

    @RequestMapping(value = "/{type}/users/{userId}", method = RequestMethod.GET)
    public Result getUser(@PathVariable String type, @PathVariable String userId) {
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        //普通会员只能查看自己
        if ("member".equalsIgnoreCase(type)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            return optionalUser.filter(user -> user.getEmail().equals(currentUserName))
                    .map(user -> new Result(true, user))
                    .orElseGet(() -> new Result(false, "user not exists Or you " +
                            "don't have access to view this user"));

        } else if ("employee".equalsIgnoreCase(type)) {
            return optionalUser.map(user -> new Result(true, user))
                    .orElseGet(() -> new Result(false, "user not exists"));
        }
        return new Result(false, "type should be either USER or MEMBER");
    }

    @RequestMapping(value = "/{type}/users/{userId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String type, @PathVariable String userId, @RequestBody User user) {
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        //普通会员只能update自己
        if ("member".equalsIgnoreCase(type)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            return optionalUser.filter(existUser -> existUser.getEmail().equals(currentUserName))
                    .map(existUser -> {
                        existUser.setFirstName(user.getFirstName());
                        existUser.setSurName(user.getSurName());
                        existUser.setAddress(user.getAddress());
                        userService.update(existUser);
                        return new Result(true, existUser);
                    })
                    .orElseGet(() -> new Result(false, "you " +
                            "don't have access to update this user"));

        } else if ("employee".equalsIgnoreCase(type)) {
            return optionalUser.map(existUser -> {
                existUser.setFirstName(user.getFirstName());
                existUser.setSurName(user.getSurName());
                existUser.setAddress(user.getAddress());
                userService.update(existUser);
                return new Result(true, existUser);
            }).orElseGet(() -> new Result(false, "user not exists"));
        }
        return new Result(false, "type should be either USER or MEMBER");
    }


    @RequestMapping(value = "/member/users/{userId}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        return optionalUser.filter(user -> user.getEmail().equalsIgnoreCase(currentUserName))
                .map(user -> {
                    userService.delete(Long.parseLong(userId));
                    return new Result(true, "delete success");
                })
                .orElseGet(() -> new Result(false, "you cannot delete this user"));
    }

}
