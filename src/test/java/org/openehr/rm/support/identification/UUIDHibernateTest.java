package org.openehr.rm.support.identification;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class UUIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		
		UUID intID = new UUID("128-1-1-12-15");
		
		Session session = sessionFactory.getCurrentSession();
		session.save(intID);
		UUID o = (UUID)session.load(UUID.class,
				intID.getMappingId());
		assertThat(intID, is(o));
	}

}
