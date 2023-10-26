package tester;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import pojos.Role;
import pojos.User;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

public class UnsubscribeUser {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			//create user dao instance
			UserDaoImpl dao=new UserDaoImpl();
			System.out.println(
					"Enter User ID for deleting user details");
			System.out.println(dao.deleteUserDetails(sc.nextInt()));
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
