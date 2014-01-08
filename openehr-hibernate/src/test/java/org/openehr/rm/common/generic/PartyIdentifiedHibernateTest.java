package org.openehr.rm.common.generic;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.PartyRef;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PartyIdentifiedHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {	
		
		Session session = sessionFactory.getCurrentSession();
        PartyRef pr = new PartyRef(new HierObjectID("1-2-3-4-5"),"PARTY");
        PartyIdentified pi = new PartyIdentified(pr, "party name", null);
		session.save(pi);
		PartyIdentified a1 = (PartyIdentified)session.load(PartyIdentified.class, pi.getMappingId());
		assertThat(a1, is(pi));
		
	}
}
