package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Notification;
import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.repositories.contracts.NotificationRepository;
import com.finalproject.peerreview2021.repositories.contracts.TeamInvitationRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl extends AbstractCRUDRepository<Notification> implements NotificationRepository {
    public NotificationRepositoryImpl(SessionFactory sessionFactory) {
        super(Notification.class, sessionFactory);
    }
}
