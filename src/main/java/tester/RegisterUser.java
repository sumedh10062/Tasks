package tester;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import pojos.Role;
import pojos.User;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

public class RegisterUser {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			//create user dao instance
			UserDaoImpl dao=new UserDaoImpl();
			System.out.println(
					"Enter User Details :  firstName,  lastName,  email,  password,  confirmPassword,userRole,regAmount, regDate (yr-mon-day)");
			// create a new user instance
			User newUser = new User(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(),
					Role.valueOf(sc.next().toUpperCase()), sc.nextDouble(), LocalDate.parse(sc.next()));
			System.out.println(dao.registerUser(newUser));
			
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
