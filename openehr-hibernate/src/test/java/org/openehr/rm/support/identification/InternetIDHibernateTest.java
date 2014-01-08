package org.openehr.rm.support.identification;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class InternetIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		InternetID intID = new InternetID("www.google.com");
		
		Session session = sessionFactory.getCurrentSession();
		session.save(intID);
		InternetID o = (InternetID)session.load(InternetID.class,
				intID.getMappingId());
		assertThat(intID, is(o));
	}

}
