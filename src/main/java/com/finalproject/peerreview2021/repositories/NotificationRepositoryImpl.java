package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.repositories.contracts.NotificationRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl extends AbstractCRUDRepository<Notification> implements NotificationRepository {
    public NotificationRepositoryImpl(SessionFactory sessionFactory) {
        super(Notification.class, sessionFactory);
    }
}
