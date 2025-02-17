package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 14:23
 */
@Repository
public class TeamRepositoryImpl extends AbstractCRUDSoftDeleteRepository<Team> implements TeamRepository {
    public TeamRepositoryImpl(SessionFactory sessionFactory) {
        super(Team.class, sessionFactory);
    }
}
