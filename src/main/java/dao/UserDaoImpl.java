package dao;

import pojos.Role;
import pojos.User;

import org.apache.commons.io.FileUtils;
import org.hibernate.*;
import static utils.HibernateUtils.getFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class UserDaoImpl implements UserDao {

	@Override
	public String registerUser(User newUser) {
		String mesg = "User registration failed!!!!!!!!!!";
		// 1. get hibernate session from SF(SessionFactory)
		Session session = getFactory().openSession();
		Session session2 = getFactory().openSession();
		System.out.println("same or different " + (session == session2));// false
		// 2. Begin Tx
		Transaction tx = session.beginTransaction();
		System.out.println("session open " + session.isOpen() + " is conncted " + session.isConnected());// t t
		try {
			// save user details
			Integer id = (Integer) session.save(newUser);
			// upon success : commit Tx
			tx.commit();
			mesg = "User registered with ID " + id;
			System.out.println("session open " + session.isOpen() + " is conncted " + session.isConnected());// t t

		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			// re throw the exc to the caller
			throw e;
		} finally {
			// close session
			if (session != null)
				session.close();
		}
		System.out.println("session open " + session.isOpen() + " is conncted " + session.isConnected());// f f

		return mesg;
	}

	@Override
	public String registerUserViaGetCurrentSession(User newUser) {
		String mesg = "User registration failed!!!!!!!!!!";
		// 1. get hibernate session from SF(SessionFactory)
		Session session = getFactory().getCurrentSession();0
		// 2. Begin Tx
		Transaction tx = session.beginTransaction();
		System.out.println("session open " + session.isOpen() + " is conncted " + session.isConnected());// t t
		try {
			// newUser : TRANSIENT (exists only in java heap, it's neither part of L1 cache
			// nor exists in DB)
			// save user details
			Integer id = (Integer) session.save(newUser);// newUser : PERSISTENT :
			// part of L1 cache, not a part of DB
			// upon success : commit Tx
			tx.commit();// session.flush()---> hib perform auto dirty chking upon commit --> as a result
						// DML : insert
			// session.close() --> L1 cache is destroyed , db cn rets to the conn pool
			mesg = "User registered with ID " + id;
			// newUser : DETACHED : de couped from L1 cache
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			// re throw the exc to the caller
			throw e;
		}
		return mesg;
	}// newUser : marked for GC

	@Override
	public User getUserDetailsById(int userId) {
		User user = null;// user : does not exist
		// 1. get hibernate session from SF(SessionFactory)
		Session session = getFactory().getCurrentSession();
		// 2. Begin Tx
		Transaction tx = session.beginTransaction();
		try {
			// get user details by id
			user = session.get(User.class, userId);// int ---> Integer ---> Serializable
			// user : in case of valid id --PERSISTENT : part of L1 cache n part of DB
			// upon success : commit Tx
			user = session.get(User.class, userId);
			user = session.get(User.class, userId);
			user = session.get(User.class, userId);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			// re throw the exc to the caller
			throw e;
		}

		return user;// user : DETACHED
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = null;
		String jpql = "select u from User u";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			users = session.createQuery(jpql, User.class).getResultList();
//			users = session.createQuery(jpql, User.class).getResultList();
//			users = session.createQuery(jpql, User.class).getResultList();
//			// users : List of PERSISTENT entities/pojos
			tx.commit();// hib performs auto dirty chking --session.flush() --no changes in state of L1
						// cahce vs DB --no DMLs --session.close()
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return users;// users : List of DETACHED entities
	}

	@Override
	public List<User> getUsersByDateAndRole(LocalDate startDate, LocalDate endDate, Role userRole) {
		List<User> users = null;
		String jpql = "select u from User u where u.regDate between :start and :end and u.userRole=:role";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			users = session.createQuery(jpql, User.class).setParameter("start", startDate).setParameter("end", endDate)
					.setParameter("role", userRole).getResultList(); // list of persistent entities
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return users;// list of DETACHED entities
	}

	@Override
	public List<String> getUserFirstNamesByRole(Role userRole) {
		List<String> firstNames = null;
		String jpql = "select u.firstName from User u where u.userRole=:role";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			firstNames = session.createQuery(jpql, String.class).setParameter("role", userRole).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return firstNames;
	}

	@Override
	public List<User> getPartialUserDetails(LocalDate date, Role userRole) {
		List<User> users = null;
		String jpql = "select new pojos.User(lastName,regAmount,regDate) from User u where u.regDate > :dt and u.userRole =:role";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			users = session.createQuery(jpql, User.class).setParameter("dt", date).setParameter("role", userRole)
					.getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return users;
	}

	@Override
	public String changePassword(String email1, String oldPwd, String newPwd) {
		User user = null;
		String mesg = "Password updation failed !!!!!!!!!!";
		String jpql = "select u from User u where u.email=:em and u.password=:pass";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			user = session.createQuery(jpql, User.class).setParameter("em", email1).setParameter("pass", oldPwd)
					.getSingleResult();
			// => valid user login , user : PERSISTENT
			user.setPassword(newPwd);// modifying the state PERSISTENT entity
			// session.evict(user);//user : DETACHED
			tx.commit();// Hibernate performs auto dirty chking -- session.flush() --> DML : update
			// L1 cache is destoryed n cn rets to db cn pool
			mesg = "Update user password!";
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// user : DEATACHED
		user.setPassword("444444444");// hibernate CAN NOT reflect the changes made to detached entity --> DB
		return mesg;
	}

	@Override
	public String applyBulkDiscount(double discount, LocalDate regDate1) {
		String mesg = "Bulk updation failed";
		String jpql = "update User u set u.regAmount=u.regAmount-:disc where u.regDate < :date";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			int updatedRowCount = session.createQuery(jpql).setParameter("disc", discount)
					.setParameter("date", regDate1).executeUpdate();
			mesg = "Applied disocunt to " + updatedRowCount + " users....";
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

	@Override
	public String deleteUserDetails(int userId) {
		String mesg = "Un subscribing user failed!!!!!!";
		User user = null;
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			// 3. get user details : session.get
			user = session.get(User.class, userId);
			if (user != null) {
				// user : PERSISTENT , valid ---> mark entity for removal
				session.delete(user);
				// user : REMOVED i.e marked for removal in L1 cache)
				mesg = "User " + user.getLastName() + " un subscribed!";
			}
			tx.commit();// hib auto dirty chking : session.flush() --DML : delete --session.close()
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// user : TRANSIENT
		return mesg;
	} // user : marked for GC !

	@Override
	public String saveImage(int userId, String imageFilePath) throws IOException {
		String mesg = null;
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin tx
		Transaction tx = session.beginTransaction();
		try {
			// 3. get user details from it's id : session.get
			User user = session.get(User.class, userId);
			// null checking
			if (user != null) {
				// => valid user, user : PERSISTENT
				// validate file path
				// 4. Create File class instance => abstract file path
				File file = new File(imageFilePath);
				if (file.isFile() && file.canRead()) {
					// => valid readbale file
					user.setImage(FileUtils.readFileToByteArray(file));// modifying the state of persistent entity
					mesg = "Saving image successful for User : " + user.getFirstName();
				} else
					mesg = "Saving Image Failed : Invalid image file !!!!!!!!!!!!!";

			} else
				mesg = "Saving Image Failed : Invalid user ID !!!!!!!!!";
			tx.commit();// auto dirty chking : update
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

}
