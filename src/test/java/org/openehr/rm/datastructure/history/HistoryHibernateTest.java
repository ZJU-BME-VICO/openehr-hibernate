package org.openehr.rm.datastructure.history;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.datastructure.DataStructureHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemList;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.quantity.datetime.DvDuration;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HistoryHibernateTest extends DataStructureHibernateTestBase {

	@Test
	public void testSaveLoad() {

		List<Locatable> items = new ArrayList<Locatable>();
		items.add(new Element("at0014", new DvText("element 1"), new DvText(
				"text 1")));
		items.add(new Element("at0015", new DvText("element 2"), new DvText(
				"text 2")));
		items.add(new Element("at0016", new DvText("element 3"), new DvText(
				"text 3")));

		ItemList itemList = new ItemList(null, "at0001", text(ELEMENT_NAME),
				null, null, null, null, items);
		IntervalEvent<ItemList> intervalEvent = new IntervalEvent<ItemList>(
				null, "at0004", text("interval event"), null, null, null, null,
				new DvDateTime("2006-07-07T10:59:00"), itemList, null,
				DvDuration.getInstance("PT30m"), codedText("mean", "meanCode"),
				0, TestTerminologyService.getInstance());

		List<Event<ItemList>> intEvent = new ArrayList<Event<ItemList>>();
		intEvent.add(intervalEvent);
		History<ItemList> history = new History<ItemList>(null, "at0005",
				text(NAME), null, null, null, null, new DvDateTime(TIME),
				intEvent, DvDuration.getInstance("PT1h"),
				DvDuration.getInstance("PT3h"), null);

		Session session = sessionFactory.getCurrentSession();
		session.save(history);
		History<ItemList> a1 = (History<ItemList>) session.load(History.class,
				history.getMappingId());
		assertThat(a1, is(history));

	}

	/* static fields */
	private static final String NAME = "history";

	private static final String ELEMENT_NAME = "element name";

	private static final String TIME = "2006-07-07T10:29:00";

}
