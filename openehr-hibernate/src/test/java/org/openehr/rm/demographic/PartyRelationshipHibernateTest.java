/*
 * Copyright (C) 2004 Rong Chen, Acode HB, Sweden
 * All rights reserved.
 *
 * The contents of this software are subject to the FSF GNU Public License 2.0;
 * you may not use this software except in compliance with the License. You may
 * obtain a copy of the License at http://www.fsf.org/licenses/gpl.html
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 */
package org.openehr.rm.demographic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.datatypes.quantity.DvInterval;
import org.openehr.rm.datatypes.quantity.datetime.DvDate;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.UIDBasedID;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class PartyRelationshipHibernateTest extends DemographicHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	
		
		UIDBasedID oid = oid("1.5.2.34.0.4.3");
		String meaning = "at0000";
		DvText name = text("father");
		DvInterval<DvDate> time = new DvInterval<DvDate>(date("1980-05-13"),
				null);
		ItemStructure details = itemSingle("father to son");
		ObjectRef source = new ObjectRef(oid("1.9.0.8.57.34.25"), "LOCAL",
				"PARTY");
		ObjectRef target = new ObjectRef(oid("1.9.8.0.70.78.0"), "LOCAL",
				"PARTY");

		PartyRelationship pr = new PartyRelationship(oid, meaning, name, null, null, null, null,
				details, time, source, target);

		Session session = sessionFactory.getCurrentSession();
		session.save(pr);
		PartyRelationship a1 = (PartyRelationship)session.load(PartyRelationship.class, pr.getMappingId());
		assertThat(a1, is(pr));

    }

}
