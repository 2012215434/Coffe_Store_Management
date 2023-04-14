package com.coffee.controller;

import com.coffee.entity.Notification;
import com.coffee.entity.Result;
import com.coffee.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/employee/news", method = RequestMethod.POST)
    public Result postNews(@RequestBody Notification notification) {
        try {
            validate(notification);
            notificationService.create(notification);
            return new Result(true, "post news success");
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    private void validate(Notification notification) {
        if (StringUtils.isEmpty(notification.getType())) {
            throw new IllegalArgumentException("notification type is null");
        }
        if (StringUtils.isEmpty(notification.getBody())) {
            throw new IllegalArgumentException("notification body is null");
        }
    }

    @RequestMapping(value = "/member/news", method = RequestMethod.GET)
    public Result getAllNews() {
        return new Result(true, notificationService.findAll());
    }

    @RequestMapping(value = "/member/news/{newsId}", method = RequestMethod.GET)
    public Result getNews(@PathVariable String newsId) {
        return new Result(true, notificationService.findById(Long.parseLong(newsId)));
    }

    @RequestMapping(value = "/public/news", method = RequestMethod.GET)
    public Result getPublicNews() {
        return new Result(true, notificationService.findAllByType("public"));
    }

}
