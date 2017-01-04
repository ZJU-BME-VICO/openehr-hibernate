package org.openehr.rm.datatypes.quantity.datetime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class DvDateHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {

		List<DvDate> textList = new ArrayList<DvDate>();

		textList.add(new DvDate("2001-01-15"));
		textList.add(new DvDate("20011015"));
		textList.add(new DvDate(2001, 12, 15));
		textList.add(new DvDate(2000, 10));

		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < textList.size(); i++) {
			session.save(textList.get(i));
		}

		session.flush();
		session.clear();

		for (int i = 0; i < textList.size(); i++) {
			DvDate actual = (DvDate) session.load(DvDate.class, textList.get(i)
					.getMappingId());
			assertThat(actual.equals(textList.get(i)), is(true));
		}

	}

}
