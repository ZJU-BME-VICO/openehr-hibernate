package org.openehr.rm.datatypes.quantity.datetime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class DvDurationHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		DvDuration expect = new DvDuration(0, 0, 0, 10, 20, 30, 40, .5);

		Session session = sessionFactory.getCurrentSession();
		session.save(expect);
		session.flush();
		session.clear();
		DvDuration actual = (DvDuration) session.load(DvDuration.class,
				expect.getMappingId());
		assertThat(actual.equals(expect), is(true));

	}

}
