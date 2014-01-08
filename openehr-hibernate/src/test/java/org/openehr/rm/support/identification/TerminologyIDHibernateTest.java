package org.openehr.rm.support.identification;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.support.identification.TerminologyID;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class TerminologyIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		Session session = sessionFactory.getCurrentSession();
		List<TerminologyID> termList = new ArrayList<TerminologyID>();
		for (int i = 0; i < STRING_VALUE.length; i++) {
			TerminologyID term = new TerminologyID(STRING_VALUE[i]);
			termList.add(term);
			session.save(term);
		}

		for (int i = 0; i < STRING_VALUE.length; i++) {
			TerminologyID o = (TerminologyID)session.load(TerminologyID.class,
					termList.get(i).getMappingId());
			if (o != null) {
				assertTID(o, i);
			}
		}
	}

	private void assertTID(TerminologyID tid, int i) {
		assertThat("value", tid.getValue(), is(STRING_VALUE[i]));
		assertThat("name", tid.name(), is(SECTIONS[i][0]));
		assertThat("version", tid.versionID(), is(SECTIONS[i][1]));
	}

	private static final String[] STRING_VALUE = { "snomed-ct", "ICD9(1999)" };

	private static final String[][] SECTIONS = { { "snomed-ct", null },
			{ "ICD9", "1999" } };
}
