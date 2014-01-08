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

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ActionHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

        ItemStructure description = list("list description");
        ItemStructure protocol = list("list protocol");
        Archetyped arch = new Archetyped(
                new ArchetypeID("openehr-ehr_rm-action.XYZ.v2"),
                "1.1");
        ISMTransition ismT = new ISMTransition(TestCodeSetAccess.ISM_ACTIVE, 
        		null, null, ts);
        Action action = new Action(null, "at0001", new DvText("action"),
                arch, null, null, null, lang, encoding, subject(), provider(), 
                null, null, protocol, null, new DvDateTime(), description, 
                ismT, null, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(action);
		Action a1 = (Action)session.load(Action.class, action.getMappingId());
		assertThat(a1, is(action));

    }

}
