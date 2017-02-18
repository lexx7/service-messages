package com.lexx7.servicemessages.business.service;

import com.lexx7.servicemessages.model.entity.Message;
import com.lexx7.servicemessages.model.entity.Message_;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.model.entity.User_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService userService;

    @Override
    public Long createMessage(Message message) {
        message.setCreateTime(new Date());

        em.persist(message);
        LOGGER.debug("Create message: " + message.getId());

        return message.getId();
    }

    @Override
    public void removeAddress(Long id) {
        LOGGER.debug("Remove message id=" + id.toString());

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<Message> criteriaDelete = criteriaBuilder.createCriteriaDelete(Message.class);
        Root root = criteriaDelete.from(Message.class);
        Predicate predicate = criteriaBuilder.equal(root.get(Message_.id), id);
        criteriaDelete.where(predicate);

        em.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public Message getMessage(Long id) {
        Message message = em.find(Message.class, id);
        LOGGER.debug("Find message id=" + id);

        if (message == null) {
            throw new IllegalStateException("Message not found id=" + id);
        }

        return message;
    }

    @Override
    public List<Message> getList(Integer page, Integer limit, String sidx, String sord, User user) {

        LOGGER.debug("MessageService getList: page: " + page + ", limit: " + limit + ", sidx: " + sidx +
                ", sord: " + sord + "userId: " + user.getId().toString());

        Integer start = limit * page - limit;

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Message> criteria = criteriaBuilder.createQuery(Message.class);

        Root<Message> root = criteria.from(Message.class);
        Join<Message, User> join = root.join(Message_.fromUser);
        Join<Message, User> joinTo = root.join(Message_.toUser);

        criteria.select(root);

        if (!(user.isAdmin())) {
            Predicate toUser = criteriaBuilder.equal(root.get(Message_.toUser), user);
            Predicate fromUser = criteriaBuilder.equal(root.get(Message_.fromUser), user);
            criteria.where(criteriaBuilder.or(toUser, fromUser));
        }

        switch (sidx) {
            case "fromUser":
                if (sord.contains("desc")) {
                    List<Order> ord = new ArrayList<>();
                    ord.add(criteriaBuilder.desc(join.get(User_.lastName)));
                    ord.add(criteriaBuilder.desc(join.get(User_.firstName)));
                    ord.add(criteriaBuilder.desc(join.get(User_.middleName)));
                    criteria.orderBy(ord);
                } else {
                    List<Order> ord = new ArrayList<> ();
                    ord.add(criteriaBuilder.asc(join.get(User_.lastName)));
                    ord.add(criteriaBuilder.asc(join.get(User_.firstName)));
                    ord.add(criteriaBuilder.asc(join.get(User_.middleName)));
                    criteria.orderBy(ord);
                }
                break;
            case "toUser":
                if (sord.contains("desc")) {
                    List<Order> ord = new ArrayList<>();
                    ord.add(criteriaBuilder.desc(joinTo.get(User_.lastName)));
                    ord.add(criteriaBuilder.desc(joinTo.get(User_.firstName)));
                    ord.add(criteriaBuilder.desc(joinTo.get(User_.middleName)));
                    criteria.orderBy(ord);
                } else {
                    List<Order> ord = new ArrayList<> ();
                    ord.add(criteriaBuilder.asc(joinTo.get(User_.lastName)));
                    ord.add(criteriaBuilder.asc(joinTo.get(User_.firstName)));
                    ord.add(criteriaBuilder.asc(joinTo.get(User_.middleName)));
                    criteria.orderBy(ord);
                }
                break;
            case "createDate":
                if (sord.contains("desc")) {
                    criteria.orderBy(criteriaBuilder.desc(root.get(Message_.createTime)));
                } else {
                    criteria.orderBy(criteriaBuilder.asc(root.get(Message_.createTime)));
                }
                break;
            case "theme":
                if (sord.contains("desc")) {
                    criteria.orderBy(criteriaBuilder.desc(root.get(Message_.theme)));
                } else {
                    criteria.orderBy(criteriaBuilder.asc(root.get(Message_.theme)));
                }
                break;
            default:
            	throw new UnsupportedOperationException("Unsupported sidx: " + sidx);
        }

        return em.createQuery(criteria).setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @Override
    public Long getCountMessages(User user) {
        LOGGER.debug("MessageService getMessageByToUserAndFromUser: user=" + user.getId().toString());

        // Total count elements
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
        Root<Message> root = criteria.from(Message.class);
        criteria.select(criteriaBuilder.count(root));

        if (!(user.isAdmin())) {
            Predicate toUser = criteriaBuilder.equal(root.get(Message_.toUser), user);
            Predicate fromUser = criteriaBuilder.equal(root.get(Message_.fromUser), user);
            criteria.where(criteriaBuilder.or(toUser, fromUser));
        }

        return (Long) em.createQuery(criteria).getSingleResult();
    }
}
