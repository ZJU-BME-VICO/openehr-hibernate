package org.openehr.rm.datatypes.quantity.datetime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class DvTimeHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		List<DvTime> textList = new ArrayList<DvTime>();

		textList.add(new DvTime(20, 10, 55, 0.133, null));
		textList.add(new DvTime(20, 10, 55, null));
		textList.add(new DvTime(0, 0, 59, TimeZone.getTimeZone("UTC")));
		textList.add(new DvTime(9, 4, 59, TimeZone.getTimeZone("GMT+10")));

		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < textList.size(); i++) {
			session.save(textList.get(i));
		}

		session.flush();
		session.clear();

		for (int i = 0; i < textList.size(); i++) {
			DvTime actual = (DvTime) session.load(DvTime.class, textList.get(i)
					.getMappingId());
			assertThat(actual.equals(textList.get(i)), is(true));
		}

	}

}
