package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl extends AbstractCRUDSoftDeleteRepository<User> implements UserRepository {

    private final SessionFactory sessionFactory;


    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> filter(Optional<String> username, Optional<String> email,
                             Optional<Integer> phone) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> filter = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            username.ifPresent(value -> {
                filter.add(" username like :username ");
                params.put("username", "%" + value + "%");
            });

            email.ifPresent(value -> {
                filter.add(" email like :email ");
                params.put("email", "%" + value + "%");
            });

            phone.ifPresent(value -> {
                filter.add(" phoneNumber = :phone ");
                params.put("phone", value);
            });
            stringBuilder.append(" from User where ").append(String.join(" and ", filter));

            Query<User> query = session.createQuery(stringBuilder.toString(), User.class);
            query.setProperties(params);

            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Filter", "with this name or", "value");
            }

            return result;
        }
    }
}
