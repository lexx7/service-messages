package com.lexx7.servicemessages.business.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.model.entity.User_;
import com.lexx7.servicemessages.model.security.SimpleUserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class); 
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Long createUser(User user) {
		user.setCreateTime(new Date());
		em.persist(user);
		LOGGER.debug("User saved: " + user.getId() +", " + user.getPassword());
		return user.getId();
	}
	
	@Override
	public void updateUser(User user) {
		user.setEditTime(new Date());
		em.merge(user);
		LOGGER.debug("User edit: " + user.getId() +", " + user.getPassword());
	}

	@Override
	public User getUser(Long id) {
		User user = em.find(User.class, id);
		if (user == null) {
			throw new IllegalStateException("User not found: " + id);
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		LOGGER.debug("loadUserByUsername: login: " + login);
		User user = getUserByLogin(login);
		
		SimpleUserDetails userDetails = new SimpleUserDetails();
		userDetails.setEnabled(user.isActive());
		userDetails.setUsername(user.getLogin());
		userDetails.setPassword(user.getPassword());
		userDetails.setAdmin(user.isAdmin());
		LOGGER.debug("loadUserByUsername: " + user.getId() + ", " + userDetails.getUsername());
		return userDetails;
	}

	@Override
	public User getUserByLogin(String login) {
		LOGGER.debug("getUserByLogin: login: " + login);
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
		
		Root<User> root = criteria.from(User.class);
		Predicate loginPredicate = criteriaBuilder.equal(root.get(User_.login), login);
		
		return em.createQuery(criteria.where(loginPredicate)).getSingleResult();
	}

	@Override
	public List<User> getAllUsers() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
		Root root = criteria.from(User.class);

		criteria.where(criteriaBuilder.equal(root.get(User_.active), true));

		List<Order> ord = new ArrayList<>();
		ord.add(criteriaBuilder.desc(root.get(User_.lastName)));
		ord.add(criteriaBuilder.desc(root.get(User_.firstName)));
		ord.add(criteriaBuilder.desc(root.get(User_.middleName)));
		criteria.orderBy(ord);

		return em.createQuery(criteria).getResultList();
	}

	@Override
	public boolean changePwd(Long id, String password) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaUpdate<User> update = criteriaBuilder.createCriteriaUpdate(User.class);
		Root root = update.from(User.class);
		update.set(root.get(User_.password), password);
		Predicate idPredicate = criteriaBuilder.equal(root.get(User_.id), id);
		update.where(idPredicate);

		em.createQuery(update).executeUpdate();

		return true;
	}

    @Override
    public boolean changeRole(Long id, Boolean admin) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaUpdate<User> update = criteriaBuilder.createCriteriaUpdate(User.class);
		Root root = update.from(User.class);
		update.set(root.get(User_.admin), admin);
		Predicate idPredicate = criteriaBuilder.equal(root.get(User_.id), id);
		update.where(idPredicate);

		em.createQuery(update).executeUpdate();

		return true;
    }

    @Override
	public List<User> getList(Integer page, Integer limit, String sidx, String sord) {

		LOGGER.debug("User getList: page: " + page.toString() + ", limit: " + limit.toString() + ", sidx: " +
				sidx + ", sord: " + sord);

		Integer start = limit * page - limit;

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);

		Root<User> root = criteria.from(User.class);

		criteria.select(root);
		criteria.where(criteriaBuilder.equal(root.get(User_.active), true));

		switch (sidx) {
			case "fio":
				if (sord.contains("desc")) {
					List<Order> ord = new ArrayList<>();
					ord.add(criteriaBuilder.desc(root.get(User_.lastName)));
					ord.add(criteriaBuilder.desc(root.get(User_.firstName)));
					ord.add(criteriaBuilder.desc(root.get(User_.middleName)));
					criteria.orderBy(ord);
				} else {
					List<Order> ord = new ArrayList<> ();
					ord.add(criteriaBuilder.asc(root.get(User_.lastName)));
					ord.add(criteriaBuilder.asc(root.get(User_.firstName)));
					ord.add(criteriaBuilder.asc(root.get(User_.middleName)));
					criteria.orderBy(ord);
				}
				break;
			case "email":
				if (sord.contains("desc")) {
					criteria.orderBy(criteriaBuilder.desc(root.get(User_.email)));
				} else {
					criteria.orderBy(criteriaBuilder.asc(root.get(User_.email)));
				}
				break;
			case "login":
				if (sord.contains("desc")) {
					criteria.orderBy(criteriaBuilder.desc(root.get(User_.login)));
				} else {
					criteria.orderBy(criteriaBuilder.asc(root.get(User_.login)));
				}
				break;
			case "role":
				if (sord.contains("desc")) {
					criteria.orderBy(criteriaBuilder.desc(root.get(User_.admin)));
				} else {
					criteria.orderBy(criteriaBuilder.asc(root.get(User_.admin)));
				}
				break;
			default:
				throw new UnsupportedOperationException("Unsupported sidx: " + sidx);
		}

		return em.createQuery(criteria).setFirstResult(start).setMaxResults(limit).getResultList();
	}

	@Override
	public Long getCountUsers() {
		LOGGER.debug("User getParametersForGrid");

		// Total count elements
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(criteriaBuilder.count(root));
		criteria.where(criteriaBuilder.equal(root.get(User_.active), true));

		return (Long) em.createQuery(criteria).getSingleResult();
	}

	public void removeUser(Long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaUpdate<User> update = criteriaBuilder.createCriteriaUpdate(User.class);
		Root root = update.from(User.class);
		update.set(root.get(User_.active), false);
		Predicate idPredicate = criteriaBuilder.equal(root.get(User_.id), id);
		update.where(idPredicate);

		em.createQuery(update).executeUpdate();
	}
}
