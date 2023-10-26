package tester;

import static utils.HibernateUtils.getFactory;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import static java.time.LocalDate.parse;
import static pojos.Role.valueOf;

public class TestJPAConstructorExpression {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			// create user dao instance
			UserDaoImpl dao = new UserDaoImpl();
			System.out.println("Enter date  n role");
			dao.getPartialUserDetails(parse(sc.next()), valueOf(sc.next().toUpperCase()))
					.forEach(u -> System.out.println("Last Name " + u.getLastName() + " reg amount " + u.getRegAmount()
							+ " reg date " + u.getRegDate()));
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
