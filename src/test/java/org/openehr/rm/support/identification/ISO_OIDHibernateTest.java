package org.openehr.rm.support.identification;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ISO_OIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		
        String value = "1.2.840.113554.1.2.2";
        ISO_OID intID = new ISO_OID(value);
		
		Session session = sessionFactory.getCurrentSession();
		session.save(intID);
		ISO_OID o = (ISO_OID)session.load(ISO_OID.class,
				intID.getMappingId());
		assertThat(intID, is(o));
	}

}
