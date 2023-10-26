package tester;

import static utils.HibernateUtils.getFactory;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import static java.time.LocalDate.parse;
import static pojos.Role.valueOf;

public class ListAllUsersByDateAndRole {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			// create user dao instance
			UserDaoImpl dao = new UserDaoImpl();
			System.out.println("Enter begin date , end date n role");
			dao.getUsersByDateAndRole
			(parse(sc.next()), parse(sc.next()), valueOf(sc.next().toUpperCase()))
					.forEach(System.out::println);
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
