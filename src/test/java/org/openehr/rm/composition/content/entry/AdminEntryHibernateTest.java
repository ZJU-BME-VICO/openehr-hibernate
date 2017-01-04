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
package org.openehr.rm.composition.content.entry;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemList;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datatypes.quantity.datetime.DvDate;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ArchetypeID;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class AdminEntryHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

        Archetyped archetypeDetails = new Archetyped(
                new ArchetypeID("openehr-ehr_rm-adminEntry.date.v2"),
                "1.0.2");        
        List<Locatable> items = new ArrayList<Locatable>();
        items.add(new Element(("at0001"), "header", new DvText("date")));
        items.add(new Element(("at0002"), "value",	new DvDate("2008-05-17")));
        ItemList itemList = new ItemList("at0003", "item list", items);        
        AdminEntry adminEntry = new AdminEntry(null, "at0004", new DvText("admin entry"),
        		archetypeDetails, null, null, null, lang, encoding, 
        		subject(), provider(), null, null, itemList, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(adminEntry);
		AdminEntry a1 = (AdminEntry)session.load(AdminEntry.class, adminEntry.getMappingId());
		assertThat(a1, is(adminEntry));

    }

}
