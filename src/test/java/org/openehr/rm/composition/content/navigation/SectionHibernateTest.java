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
package org.openehr.rm.composition.content.navigation;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.composition.content.ContentItem;
import org.openehr.rm.composition.content.entry.Observation;
import org.openehr.rm.datatypes.text.DvText;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class SectionHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

        List<ContentItem> items = new ArrayList<ContentItem>();
        observationTwo = observation("observation 2");
        items.add(observationTwo);
        
        sectionThree = section("section 3");
        items.add(sectionThree);
        
        sectionTwo = new Section("at0000", new DvText("section 2"), items);
        items = new ArrayList<ContentItem>();
        items.add(sectionTwo);
        
        observationOne = observation("observation 1"); 
        items.add(observationOne);
        section = new Section("at0000", new DvText("section"), items);

		Session session = sessionFactory.getCurrentSession();
		session.save(section);
		Section a1 = (Section)session.load(Section.class, section.getMappingId());
		assertThat(a1, is(section));

    }
	
    private Section section;
    private Section sectionTwo;
    private Section sectionThree;
    private Observation observationOne;
    private Observation observationTwo;

}
