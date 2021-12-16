package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.Team;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WorkItemRepositoryImpl extends AbstractCRUDSoftDeleteRepository<WorkItem> implements WorkItemRepository {

    private final SessionFactory sessionFactory;

    public WorkItemRepositoryImpl(SessionFactory sessionFactory) {
        super(WorkItem.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<WorkItem> filter(Optional<String> title, Optional<String> status,
                                 Optional<String> reviewer, Optional<String> sortParam) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> filter = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            title.ifPresent(value -> {
                filter.add(" w.title like :title ");
                params.put("title", "%" + value + "%");
            });

            status.ifPresent(value -> {
                filter.add(" w.status.name like :status ");
                params.put("status", "%" + value + "%");
            });

            reviewer.ifPresent(value -> {
                filter.add(" u.username like :reviewer ");
                params.put("reviewer", "%" + value + "%");
            });

            String sortParamStr = "";
            if (sortParam.isPresent()) {
                sortParamStr = generateQueryStringFromSortParam(sortParam);
            }

            stringBuilder.append(" select w from WorkItem w left join Reviewer r on w.id=r.workItem " +
                    "left join User u on r.user=u.id where w.active = true and ").append(String.join(" and ", filter));
            stringBuilder.append(sortParamStr);

            Query<WorkItem> query = session.createQuery(stringBuilder.toString(), WorkItem.class);
            query.setProperties(params);

            return query.list();
        }
    }

    private String generateQueryStringFromSortParam(Optional<String> sortParam) {
        String paramStr = sortParam.get();

        switch (paramStr) {
            case "Id, ascending":
                return " order by id asc";
            case "Id, descending":
                return " order by id desc";
            case "Title, ascending":
                return " order by title asc";
            case "Title, descending":
                return " order by title desc";
            case "Status, ascending":
                return " order by status.id asc";
            case "Status, descending":
                return " order by status.id desc";
        }
        return "Did not enter switch case.";
    }

    @Override
    public List<WorkItem> getAllWorkItemsForUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<WorkItem> query = session.createQuery(
                    "from WorkItem" + " where createdBy.id = :userId and active = TRUE", WorkItem.class);
            query.setParameter("userId", user.getId());

            return query.list();
        }
    }

    @Override
    public List<WorkItem> getAllWorkItemsForReviewer(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<WorkItem> query = session.createQuery(
                    "select w from WorkItem w join Reviewer r " +
                            "on w.id=r.workItem.id " +
                            "where r.user.id = :userId and w.active = TRUE", WorkItem.class);
            query.setParameter("userId", user.getId());

            return query.list();
        }
    }

    @Override
    public List<WorkItem> showAllWorkItemsForTeam(Team team) {
        try (Session session = sessionFactory.openSession()) {
            Query<WorkItem> query = session.createQuery(
                    "from WorkItem" + " where team.id = :teamId and active = TRUE", WorkItem.class);
            query.setParameter("teamId", team.getId());

            return query.list();
        }
    }
}
