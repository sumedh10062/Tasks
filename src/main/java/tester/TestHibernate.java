package tester;

import org.hibernate.SessionFactory;
import static utils.HibernateUtils.getFactory;

public class TestHibernate {

	public static void main(String[] args) {
		try(SessionFactory sf=getFactory())
		{
			System.out.println("Hibernate up n running "+sf);
		} // JVM : sf.close ==> closing of SF : cn pool !

	}

}
