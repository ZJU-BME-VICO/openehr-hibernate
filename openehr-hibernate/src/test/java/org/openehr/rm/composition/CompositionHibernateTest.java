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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.composition.content.ContentItem;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.terminology.SimpleTerminologyService;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class CompositionHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	
        
        DvText name = new DvText("composition2");
        UIDBasedID id = new HierObjectID("1.11.2.4.22.5.3");
        List<ContentItem> content = new ArrayList<ContentItem>();
        content.add(section("section one"));
        
        TerminologyService ts = SimpleTerminologyService.getInstance();
        CodePhrase lang = new CodePhrase("ISO_639-1", "en");
        CodePhrase charset = new CodePhrase("IANA_character-sets", "UTF-8");
        CodePhrase event = new CodePhrase("openehr", "433");
        DvCodedText category = new DvCodedText("event", lang, charset, event, 
        		ts);
        CodePhrase territory = new CodePhrase("ISO_3166-1", "SE");
        
        Archetyped archetypeDetails = new Archetyped(new ArchetypeID(
                "openehr-ehr_rm-Composition.physical_examination.v2"), "1.0");
        Composition composition = new Composition(id, "at0001", name, archetypeDetails,
                null, null, null, content, lang, context(), 
                provider(), category, territory, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(composition);
		Composition a1 = (Composition)session.load(Composition.class, composition.getMappingId());
		assertThat(a1, is(composition));

    }

}
