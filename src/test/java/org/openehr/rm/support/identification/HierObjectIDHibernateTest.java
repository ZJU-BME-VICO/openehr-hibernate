package org.openehr.rm.support.identification;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HierObjectIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		Session session = sessionFactory.getCurrentSession();
		List<HierObjectID> termList = new ArrayList<HierObjectID>();
        for (int i = 0; i < STRING_VALUES.length; i++) {
        	HierObjectID term = new HierObjectID(STRING_VALUES[i]);
			termList.add(term);
			session.save(term);
		}

		for (int i = 0; i < STRING_VALUES.length; i++) {
			HierObjectID o = (HierObjectID)session.load(HierObjectID.class,
					termList.get(i).getMappingId());
			if (o != null) {
				assertHOID(o, i);
			}
		}
	}

    private void assertHOID(HierObjectID hoid, int i) {      
		assertThat("value", hoid.getValue(), is(STRING_VALUES[i]));
		assertThat("root", hoid.root().getValue(), is(SECTIONS[i][0]));
		assertThat("extension", hoid.extension(), is(SECTIONS[i][1]));
    }

    private static final String[][] SECTIONS = {
            {"1.2.840.113554.1.2.2", "345"},
            {"1-2-840-113554-1", "789"},
            {"w123.com", "123"},
            {"1.2.840.113554.1.2.2", null},
            {"1-2-840-113554-1", null},
            {"w123.com", null}
        };

    private static final String[] STRING_VALUES = {
            "1.2.840.113554.1.2.2::345",
            "1-2-840-113554-1::789",
            "w123.com::123",
            "1.2.840.113554.1.2.2",
            "1-2-840-113554-1",
            "w123.com",
        };
}
