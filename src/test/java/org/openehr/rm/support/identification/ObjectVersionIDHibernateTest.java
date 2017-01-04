package org.openehr.rm.support.identification;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class ObjectVersionIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		List<ObjectVersionID> textList = new ArrayList<ObjectVersionID>();
		String[][] ids = { { "1.4.4.5", "1.2.840.114.1.2.2::123", "1" },
				{ "1.2.4.5", "7234-235-422-4-23::2", "2.0.0" },
				{ "1.6.1.6", "openehr.org::0.99", "2.1.2" } };
		for (int i = 0; i < ids.length; i++) {
			textList.add(new ObjectVersionID(ids[i][0], ids[i][1], ids[i][2]));
		}
		String[][] ids2 = { { "1-4-4-5-12", "1.2.840.114.1.2.2", "1" },
				{ "12-14-1-1-9", "7234-235-422-4-23::23", "2.0.0" },
				{ "1123-1-4-5457-7", "openehr.org", "2.1.2" } };
		for (int i = 0; i < ids2.length; i++) {
			textList.add(new ObjectVersionID(ids2[i][0], ids2[i][1], ids2[i][2]));
		}
		String[][] ids3 = { { "openehr", "1.2.840.114.1.2.2", "1" },
				{ "openehrR1-0.org", "7234-235-422-4-23::23", "2.0.0" },
		// {"openehr.org.uk", "w123.55.155::ext1", "2.1.2"}
		};
		for (int i = 0; i < ids3.length; i++) {
			textList.add(new ObjectVersionID(ids3[i][0], ids3[i][1], ids3[i][2]));
		}

		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < textList.size(); i++) {
			session.save(textList.get(i));
		}

		for (int i = 0; i < textList.size(); i++) {
			ObjectVersionID text = (ObjectVersionID) session.load(ObjectVersionID.class, textList.get(i)
					.getMappingId());
			assertThat(text, is(textList.get(i)));
		}

	}

}
