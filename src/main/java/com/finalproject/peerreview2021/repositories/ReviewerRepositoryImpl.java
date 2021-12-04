package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReviewerRepositoryImpl extends AbstractCRUDRepository<Reviewer> implements ReviewerRepository {
    private final SessionFactory sessionFactory;

    public ReviewerRepositoryImpl(SessionFactory sessionFactory) {
        super(Reviewer.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllReviewersForWorkItemAsUsers(WorkItem workItem) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "select u from User u join Reviewer r on u.id = r.user.id " +
                            "where r.workItem.id = :workitemId", User.class);
            query.setParameter("workitemId", workItem.getId());

            return query.list();
        }
    }

    @Override
    public List<Reviewer> getAllReviewersForWorkItem(WorkItem workItem) {
        try (Session session = sessionFactory.openSession()) {
            Query<Reviewer> query = session.createQuery(
                    "from Reviewer where workItem.id = :workitemId", Reviewer.class);
            query.setParameter("workitemId", workItem.getId());

            return query.list();
        }
    }
}
