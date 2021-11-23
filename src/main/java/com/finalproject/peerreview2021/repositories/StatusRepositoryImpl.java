package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Status;
import com.finalproject.peerreview2021.repositories.contracts.StatusRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 14:41
 */
@Repository
public class StatusRepositoryImpl extends AbstractReadRepository<Status> implements StatusRepository {
    public StatusRepositoryImpl(SessionFactory sessionFactory) {
        super(Status.class, sessionFactory);
    }
}
