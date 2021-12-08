package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.TeamInvitation;
import com.finalproject.peerreview2021.repositories.contracts.TeamInvitationRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TeamInvitationRepositoryImpl extends AbstractCRUDRepository<TeamInvitation> implements TeamInvitationRepository {
    public TeamInvitationRepositoryImpl(SessionFactory sessionFactory) {
        super(TeamInvitation.class, sessionFactory);
    }
}
