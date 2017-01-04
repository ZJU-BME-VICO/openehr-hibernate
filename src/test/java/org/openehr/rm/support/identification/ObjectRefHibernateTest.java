package org.openehr.rm.support.identification;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ObjectRefHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		Session session = sessionFactory.getCurrentSession();

		ObjectRef or1 = new ObjectRef(hid("1-2-80-11-1"), "LOCAL", "EHR");
		session.save(or1);
		ObjectRef o = (ObjectRef)session.load(ObjectRef.class,
				or1.getMappingId());
		assertThat(or1, is(o));
	}

	private ObjectID hid(String value) {
		return new HierObjectID(value);
	}
    
}
