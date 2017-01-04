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
package org.openehr.rm.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.datastructure.itemstructure.ItemTree;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datastructure.itemstructure.representation.Item;
import org.openehr.rm.datatypes.quantity.DvQuantity;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class GenericEntryCreationHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() throws Exception {	

		String archetypeNodeId = "at0001";
		String name = "test generic entry";
		Element element = new Element("at0002", "te",	new DvQuantity(12.0));
		List<Locatable> items = new ArrayList<Locatable>();
		items.add(element);
		ItemTree itemTree = new ItemTree("at0003", "tree", items);
		
		GenericEntry entry = new GenericEntry(archetypeNodeId, name, itemTree);

		Session session = sessionFactory.getCurrentSession();
		session.save(entry);
		GenericEntry a1 = (GenericEntry)session.load(GenericEntry.class, entry.getMappingId());
		assertThat(a1, is(entry));

    }

}
