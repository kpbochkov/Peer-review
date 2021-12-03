package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.repositories.contracts.TeamRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 14:23
 */
@Repository
public class TeamRepositoryImpl extends AbstractCRUDRepository<Team> implements TeamRepository {
    public TeamRepositoryImpl(SessionFactory sessionFactory) {
        super(Team.class, sessionFactory);
    }
}
