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
package org.openehr.rm.ehr;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.support.identification.ArchetypeID;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class EHRStatusHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

		ItemStructure otherDetails = list("list other details");
        Archetyped arch = new Archetyped(
                new ArchetypeID("openehr-ehr_rm-ehrstatus.XYZ.v2"),
                "1.1");
        EHRStatus ehrStatus = new EHRStatus(null, "at0001", text("EHR Status"),
                arch, null, null, null, subject(), true, true, otherDetails);

		Session session = sessionFactory.getCurrentSession();
		session.save(ehrStatus);
		EHRStatus a1 = (EHRStatus)session.load(EHRStatus.class, ehrStatus.getMappingId());
		assertThat(a1, is(ehrStatus));

    }

}
