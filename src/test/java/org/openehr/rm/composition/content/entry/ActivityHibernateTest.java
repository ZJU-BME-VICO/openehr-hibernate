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
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.datatypes.encapsulated.DvParsable;
import org.openehr.rm.datatypes.text.CodePhrase;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ActivityHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

		DvParsable timing = new DvParsable(new CodePhrase("test", "en"),
				new CodePhrase("test", "en"), "timing value", "fomalism", ts);
		Activity activity = new Activity("at0004", text("activity 1"),
				list("list activity"), timing,
				"openEHR-EHR-ITEM_TREE.intravenous_fluids.v1");

		Session session = sessionFactory.getCurrentSession();
		session.save(activity);
		Activity a1 = (Activity)session.load(Activity.class, activity.getMappingId());
		assertThat(a1, is(activity));

    }

}
