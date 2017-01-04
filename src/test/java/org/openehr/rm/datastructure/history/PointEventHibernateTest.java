package org.openehr.rm.datastructure.history;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.datastructure.DataStructureHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemSingle;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.DvText;

public class PointEventHibernateTest extends DataStructureHibernateTestBase {

	@Test
	public void testSaveLoad() {
		Element element = element("element name", "value");
		ItemSingle item = new ItemSingle(null, "at0001", text("point event item"), null,
				null, null, null, element);

		String nodeId = "at0002";
		DvText name = new DvText("point event");
		DvDateTime time = new DvDateTime(TIME);
		ItemSingle data = item;
		PointEvent<ItemSingle> pointEvent = new PointEvent<ItemSingle>(nodeId, name, time, data);

		Session session = sessionFactory.getCurrentSession();
		session.save(pointEvent);
		PointEvent<ItemSingle> pe = (PointEvent<ItemSingle>)session.load(PointEvent.class, pointEvent.getMappingId());
		assertThat(pe, is(pointEvent));
		
	}

	private static final String TIME = "2004-12-06T13:10:00";

}
