package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;


@Repository
public class ReviewerRepositoryImpl extends AbstractCRUDRepository<Reviewer> implements ReviewerRepository {
    public ReviewerRepositoryImpl(SessionFactory sessionFactory) {
        super(Reviewer.class, sessionFactory);
    }
}
