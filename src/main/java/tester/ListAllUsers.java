package tester;

import static utils.HibernateUtils.getFactory;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;

public class ListAllUsers {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory()) {
			System.out.println("Hibernate up n running " + sf);
			// create user dao instance
			UserDaoImpl dao = new UserDaoImpl();
			dao.getAllUsers().forEach(System.out::println);
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
