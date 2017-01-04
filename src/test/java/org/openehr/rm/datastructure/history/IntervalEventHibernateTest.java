package org.openehr.rm.datastructure.history;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.datastructure.DataStructureHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemList;
import org.openehr.rm.datastructure.itemstructure.ItemSingle;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.quantity.datetime.DvDuration;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.terminology.TestTerminologyService;

public class IntervalEventHibernateTest extends DataStructureHibernateTestBase {

	@Test
	public void testSaveLoad() {

        Element element = element("element name", "element value");
        
        ItemSingle item = new ItemSingle(null, "at0001", text("interval event item"),
                null, null, null, null, element);
        
		IntervalEvent<ItemSingle> ie = new IntervalEvent<ItemSingle>(null, "at0002", text("point event"),
	            null, null, null, null, new DvDateTime("2004-12-07T10:29:00"), item, null, 
	            DvDuration.getInstance("PT1h"), codedText("mean", "meanCode"),
	            0, TestTerminologyService.getInstance());

		Session session = sessionFactory.getCurrentSession();
		session.save(ie);
		IntervalEvent<ItemSingle> ie1 = (IntervalEvent<ItemSingle>)session.load(IntervalEvent.class, ie.getMappingId());
		assertThat(ie, is(ie1));
		
		List<Locatable> items = new ArrayList<Locatable>();
		items.add(new Element("at0014", new DvText("element 1"), new DvText("text 1")));
		items.add(new Element("at0015", new DvText("element 2"), new DvText("text 2")));
		items.add(new Element("at0016", new DvText("element 3"), new DvText("text 3")));
		
		ItemList itemList = new ItemList(null, "at0001", text("element name"),
				null, null, null, null, items);
		IntervalEvent<ItemList> intervalEvent = new IntervalEvent<ItemList>(null, "at0004",
				text("interval event"), null, null, null, null, new DvDateTime(
						"2006-07-07T10:59:00"), itemList, null, DvDuration
						.getInstance("PT30m"), codedText("mean", "meanCode"),
				0, TestTerminologyService.getInstance());

		session.save(intervalEvent);
		IntervalEvent<ItemList> intervalEvent1 = (IntervalEvent<ItemList>)session.load(IntervalEvent.class, intervalEvent.getMappingId());
		assertThat(intervalEvent, is(intervalEvent1));
		
	}

}
