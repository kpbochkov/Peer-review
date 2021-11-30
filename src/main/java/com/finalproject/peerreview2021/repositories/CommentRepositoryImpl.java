package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Comment;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.CommentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractCRUDRepository<Comment> implements CommentRepository {
    private final SessionFactory sessionFactory;

    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        super(Comment.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deleteUserComments(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "delete from Comment" + " where user.id = :userId");
            query.setParameter("userId", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public List<Comment> getAllWorkItemComments(WorkItem workitem) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery(
                    "from Comment" + " where workItem.id = :workitemId", Comment.class);
            query.setParameter("workitemId", workitem.getId());

            return query.list();
        }
    }
}
