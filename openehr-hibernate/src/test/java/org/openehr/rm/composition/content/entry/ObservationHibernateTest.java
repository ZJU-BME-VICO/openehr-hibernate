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
import org.openehr.rm.datastructure.history.History;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.terminology.SimpleTerminologyService;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ObservationHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	
        
		ItemStructure protocol = list("list protocol");
        History<ItemStructure> data = event("data");
        History<ItemStructure> state = event("state");
        Archetyped arch = new Archetyped(
                new ArchetypeID("openehr-ehr_rm-observation.physical_examination.v3"),
                "1.1");
        TerminologyService termServ = SimpleTerminologyService.getInstance();    	
    	
        CodePhrase language = new CodePhrase("ISO_639-1", "en");
		CodePhrase encoding = new CodePhrase("IANA_character-sets", "UTF-8");
		
		Observation observation = new Observation(null, "at000", text("observation"),
                arch, null, null, null, language, encoding, subject(), 
                provider(), null, null, protocol, null, data, state, termServ);

		Session session = sessionFactory.getCurrentSession();
		session.save(observation);
		Observation a1 = (Observation)session.load(Observation.class, observation.getMappingId());
		assertThat(a1, is(observation));

    }

}
