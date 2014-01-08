package org.openehr.rm.common.archetyped;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.support.identification.ArchetypeID;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ArchetypedHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		Session session = sessionFactory.getCurrentSession();
		Archetyped expect = new Archetyped(
				aid("openehr-ehr_rm-section.physical_examination.v2"), "1.1");
		session.save(expect);
		session.flush();
		session.clear();
		Archetyped actual = (Archetyped) session.load(Archetyped.class,
				expect.getMappingId());
		assertThat(actual, is(expect));
	}

	private ArchetypeID aid(String value) {

		return new ArchetypeID(value);

	}
}
