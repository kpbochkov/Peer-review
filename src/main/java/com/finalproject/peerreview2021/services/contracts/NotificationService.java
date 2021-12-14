package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;

import java.util.List;

public interface NotificationService {

    void create(Notification entity, List<User> usersToReceiveNotification);

    void update(Notification entity);

    void delete(int id);

    Notification getById(int id);

    List<Notification> getUserNotifications(User user);

    void notify(String notification, List<User> users, WorkItem workItem);

    void markUserNotificationsSeen(User user);
}
