package com.coffee.service;

import com.coffee.entity.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    List<Notification> findAllByType(String type);
}
