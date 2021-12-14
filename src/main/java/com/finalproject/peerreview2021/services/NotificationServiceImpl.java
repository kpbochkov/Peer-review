package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.repositories.contracts.NotificationRepository;
import com.finalproject.peerreview2021.services.contracts.NotificationService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
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
    public void notify(String notificationContent, List<User> users) {
        Notification notification = new Notification();
        Instant time = Instant.now();
        notification.setDescription(notificationContent);
        notification.setTime(time);
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
    public void update(Notification entity) {
        notificationRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        notificationRepository.delete(id);
    }


}
