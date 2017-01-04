package org.openehr.rm.datatypes.quantity.datetime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class DvDateTimeHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		List<DvDateTime> textList = new ArrayList<DvDateTime>();

		textList.add(new DvDateTime("2003-12-15T09:30:00Z"));
		textList.add(new DvDateTime(2003, 12, 15, 9, 30, 0, TimeZone
				.getTimeZone("UTC")));
		textList.add(new DvDateTime("2003-12-15T09:30:00"));
		textList.add(new DvDateTime("20020201T010000+01"));

		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < textList.size(); i++) {
			session.save(textList.get(i));
		}

		session.flush();
		session.clear();

		for (int i = 0; i < textList.size(); i++) {
			DvDateTime actual = (DvDateTime) session.load(DvDateTime.class,
					textList.get(i).getMappingId());
			assertThat(actual.equals(textList.get(i)), is(true));
		}

	}

}
