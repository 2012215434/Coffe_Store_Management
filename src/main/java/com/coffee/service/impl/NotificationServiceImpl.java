package com.coffee.service.impl;

import com.coffee.entity.Notification;
import com.coffee.repository.NotificationRepository;
import com.coffee.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void create(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void delete(Long... ids) {
        notificationRepository.deleteAllByIdInBatch(Arrays.asList(ids));
    }

    @Override
    public void update(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAllByType(String type) {
        return notificationRepository.findAllByType(type);
    }
}
