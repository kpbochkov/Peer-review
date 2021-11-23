package com.finalproject.peerreview2021.repositories;

import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.WorkItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WorkItemRepositoryImpl extends AbstractCRUDRepository<WorkItem> implements WorkItemRepository {

    private final SessionFactory sessionFactory;

    public WorkItemRepositoryImpl(SessionFactory sessionFactory) {
        super(WorkItem.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<WorkItem> filter(Optional<String> name, Optional<String> status,
                                 Optional<String> sortParam) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> filter = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            name.ifPresent(value -> {
                filter.add(" name like :name ");
                params.put("name", "%" + value + "%");
            });

            status.ifPresent(value -> {
                filter.add(" status.name like :status ");
                params.put("status", "%" + value + "%");
            });

            String sortParamStr = generateQueryStringFromSortParam(sortParam);

            stringBuilder.append(" from WorkItem where ").append(String.join(" and ", filter));
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

}
