package com.finalproject.peerreview2021.services.contracts;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.User;

import java.util.List;

public interface NotificationService extends BaseCRUDService<Notification> {

    void create(Notification entity, List<User> usersToReceiveNotification);

//    void notify(String notification, List<User> usersToReceiveNotification);
}
