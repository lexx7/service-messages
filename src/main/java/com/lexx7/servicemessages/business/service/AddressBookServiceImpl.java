package com.lexx7.servicemessages.business.service;

import com.lexx7.servicemessages.model.entity.AddressBook;
import com.lexx7.servicemessages.model.entity.AddressBook_;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.model.entity.User_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AddressBookServiceImpl implements AddressBookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long createAddress(AddressBook address) {
        em.persist(address);
        LOGGER.debug("Create address: " + address.getId());

        return address.getId();
    }

    @Override
    public AddressBook getAddress(Long id) {
        AddressBook address = em.find(AddressBook.class, id);
        LOGGER.debug("Find address id=" + id);

        if (address == null) {
            throw new IllegalStateException("Address not found id=" + id);
        }

        return address;
    }

    @Override
    public void removeAddress(Long id) {
        LOGGER.debug("removeAddress id=" + id.toString());

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<AddressBook> criteriaDelete = criteriaBuilder.createCriteriaDelete(AddressBook.class);
        Root root = criteriaDelete.from(AddressBook.class);
        Predicate predicate = criteriaBuilder.equal(root.get(AddressBook_.id), id);
        criteriaDelete.where(predicate);

        em.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public List<AddressBook> getList(Integer page, Integer limit, String sidx, String sord, User user) {

        LOGGER.debug("getList: page: " + page.toString() + ", limit: " + limit.toString() + ", sidx: " + sidx +
                ", sord: " + sord + ", user: " + user.getId().toString());

        Integer start = limit * page - limit;

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AddressBook> criteria = criteriaBuilder.createQuery(AddressBook.class);

        Root<AddressBook> root = criteria.from(AddressBook.class);
        Join<AddressBook, User> join = root.join(AddressBook_.toUser);

        criteria.select(root);

        Predicate userFrom = criteriaBuilder.equal(root.get(AddressBook_.fromUser), user);
        Predicate activeUser = criteriaBuilder.equal(join.get(User_.active), true);
        criteria.where(criteriaBuilder.and(userFrom, activeUser));

        if (sidx.contains("toUser")) {
        	List<Order> ord = new ArrayList<> ();
            if (sord.contains("desc")) {
                ord.add(criteriaBuilder.desc(join.get(User_.lastName)));
                ord.add(criteriaBuilder.desc(join.get(User_.firstName)));
                ord.add(criteriaBuilder.desc(join.get(User_.middleName)));
            } else {
                ord.add(criteriaBuilder.asc(join.get(User_.lastName)));
                ord.add(criteriaBuilder.asc(join.get(User_.firstName)));
                ord.add(criteriaBuilder.asc(join.get(User_.middleName)));
            }
            criteria.orderBy(ord);
        }

        return em.createQuery(criteria).setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @Override
    public Long getCountAddressesByUser(User user) {
        LOGGER.debug("getParametersForGrid: user: " + user.getId().toString());

        // Total count elements
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);

        Root<AddressBook> root = criteria.from(AddressBook.class);
        Join<AddressBook, User> join = root.join(AddressBook_.toUser);

        criteria.select(criteriaBuilder.count(root));
        Predicate userFrom = criteriaBuilder.equal(root.get(AddressBook_.fromUser), user);
        Predicate activeUser = criteriaBuilder.equal(join.get(User_.active), true);
        criteria.where(criteriaBuilder.and(userFrom, activeUser));

        return (Long) em.createQuery(criteria).getSingleResult();
    }

    @Override
    public AddressBook getAddressByToUserAndFromUser(User toUser, User fromUser) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AddressBook> criteria = criteriaBuilder.createQuery(AddressBook.class);

        Root<AddressBook> root = criteria.from(AddressBook.class);

        criteria.select(root);

        Predicate PtoUser = criteriaBuilder.equal(root.get(AddressBook_.toUser), toUser);
        Predicate PfromUser = criteriaBuilder.equal(root.get(AddressBook_.fromUser), fromUser);
        criteria.where(criteriaBuilder.and(PtoUser, PfromUser));

        return em.createQuery(criteria).getSingleResult();
    }
}
