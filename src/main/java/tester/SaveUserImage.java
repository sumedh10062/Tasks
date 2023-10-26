package tester;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import pojos.Role;
import pojos.User;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

public class SaveUserImage {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			//create user dao instance
			UserDaoImpl dao=new UserDaoImpl();
			System.out.println(
					"Enter User ID");
			int id=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter image file path ");			
			System.out.println(dao.saveImage(id, sc.nextLine()));
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
