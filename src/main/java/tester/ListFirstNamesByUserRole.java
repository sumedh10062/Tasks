package tester;

import static pojos.Role.valueOf;
import static utils.HibernateUtils.getFactory;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;

public class ListFirstNamesByUserRole {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			// create user dao instance
			UserDaoImpl dao = new UserDaoImpl();
			System.out.println("Enter  role");
			dao.getUserFirstNamesByRole(valueOf(sc.next().toUpperCase()))
					.forEach(System.out::println);
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
