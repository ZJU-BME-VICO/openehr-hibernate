package org.openehr.rm.common.archetyped;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FeederAuditDetailsHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() throws Exception {

		Session session = sessionFactory.getCurrentSession();
		FeederAuditDetails expect = new FeederAuditDetails("systemId", null,
				null, new DvDateTime("2004-10-12T09:00:00"), null,
				"versionid12.1");
		session.save(expect);
		session.flush();
		session.clear();
		FeederAuditDetails actual = (FeederAuditDetails) session.load(
				FeederAuditDetails.class, expect.getMappingId());
		assertThat(actual.equals(expect), is(true));

	}

}
