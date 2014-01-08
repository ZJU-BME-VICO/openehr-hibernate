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
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.rm.support.terminology.TestTerminologyAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ISMTransitionHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

		DvCodedText currentState = new DvCodedText("current state", 
        		TestTerminologyAccess.SOME_STATE);
        
        DvCodedText transition = new DvCodedText("transition", 
        		TestTerminologyAccess.SOME_TRANSITION);
        
        CodePhrase definingCode = new CodePhrase("test", "124");
        DvCodedText careflowStep = new DvCodedText("care flow Step", 
        		definingCode);
        
        TerminologyService ts = TestTerminologyService.getInstance();
        
        ISMTransition ismt = new ISMTransition(currentState, transition, 
        		careflowStep, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(ismt);
		ISMTransition a1 = (ISMTransition)session.load(ISMTransition.class, ismt.getMappingId());
		assertThat(a1, is(ismt));

    }

}
