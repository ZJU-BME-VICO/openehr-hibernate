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
package org.openehr.rm.composition;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.support.terminology.TestCodeSetAccess;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class EventContextHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() {	
		
    	DvDateTime startTime = new DvDateTime("2006-11-22T18:57:01");	
		DvCodedText setting = TestCodeSetAccess.SETTING;
    	EventContext context = new EventContext(startTime, setting, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(context);
		EventContext a1 = (EventContext)session.load(EventContext.class, context.getMappingId());
		assertThat(a1, is(context));

    }

}
