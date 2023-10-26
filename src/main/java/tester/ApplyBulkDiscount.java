package tester;

import org.hibernate.SessionFactory;

import dao.UserDaoImpl;
import pojos.Role;
import pojos.User;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import javax.xml.bind.ParseConversionEvent;

public class ApplyBulkDiscount {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Hibernate up n running " + sf);
			// create user dao instance
			UserDaoImpl dao = new UserDaoImpl();
			System.out.println("Enter discount amount n date");
			System.out.println(dao.applyBulkDiscount(sc.nextDouble(), LocalDate.parse(sc.next())));
		} // JVM : sf.close ==> closing of SF : cn pool !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
