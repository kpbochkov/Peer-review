package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.NotificationRepository;
import com.finalproject.peerreview2021.services.contracts.NotificationService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public void create(Notification entity, List<User> usersToReceiveNotification) {
        entity.setUsersWithNotification(usersToReceiveNotification);
        notificationRepository.create(entity);
    }

    @Override
    public List<Notification> getUserNotifications(User user) {
        return new ArrayList<>(user.getNotifications());
    }

    @Override
    public void notify(String notificationContent, List<User> users, WorkItem workItem) {
        Notification notification = new Notification();
        Instant time = Instant.now();
        notification.setDescription(notificationContent);
        notification.setTime(time);
        notification.setWorkItem(workItem);
        create(notification, users);
    }

    @Override
    public void markUserNotificationsSeen(User user) {
        List<Notification> notifications = getUserNotifications(user).
                stream().filter(n -> n.getSeen().equals(false)).collect(Collectors.toList());
        for (Notification notification : notifications) {
            notification.setSeen(true);
            update(notification);
        }
    }

    @Override
    public void dismissAll(User user) {
        List<Notification> notifications = getUserNotifications(user);
        user.setNotifications(new ArrayList<>());
        userService.update(user);
        for (Notification notification : notifications) {
            notificationRepository.delete(notification.getId());
        }
    }

    @Override
    public void update(Notification entity) {
        notificationRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        notificationRepository.delete(id);
    }

    @Override
    public Notification getById(int id) {
        return notificationRepository.getById(id);
    }


}
