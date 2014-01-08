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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.generic.AuditDetails;
import org.openehr.rm.composition.Composition;
import org.openehr.rm.composition.CompositionHibernateTestBase;
import org.openehr.rm.composition.content.ContentItem;
import org.openehr.rm.composition.content.navigation.Section;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;
import org.openehr.rm.support.terminology.TestTerminologyAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class VersionedCompositionHibernateTest extends CompositionHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

		HierObjectID id = new HierObjectID("1.2.4.7");
		Composition firstData = composition("at0001", "first");
		ObjectRef ehrRef = new ObjectRef(new HierObjectID("1.2.0.0.0.1.2"),
				"LOCAL", "EHR");

		VersionedComposition vc = new VersionedComposition(id, ehrRef, new DvDateTime(),
				new ObjectVersionID("1.2.4.7::1.2.40.14.1.2.2::1"), firstData,
				TestCodeSetAccess.CREATION, audit(TestCodeSetAccess.CREATION),
				contribution("1.2.3.1"), null, ts);

		Session session = sessionFactory.getCurrentSession();
		session.save(vc);
		VersionedComposition a1 = (VersionedComposition)session.load(VersionedComposition.class, vc.getMappingId());
		assertThat(a1, is(vc));

    }

	private AuditDetails audit(DvCodedText changeType) throws Exception {

		return new AuditDetails("/", provider(), new DvDateTime(), changeType,
				new DvText("desc"), TestTerminologyService.getInstance());
	}

	private ObjectRef contribution(String id) throws Exception {
		return new ObjectRef(new HierObjectID(id), "LOCAL", "CONTRIBUTION");
	}

	private Composition composition(String node, String text) throws Exception {
		DvText name = new DvText(text, lang, encoding, ts);
		UIDBasedID id = new HierObjectID("1.11.2.5.1.66.3");
		List<ContentItem> content = new ArrayList<ContentItem>();
		content.add(section());
		DvCodedText category = TestCodeSetAccess.EVENT;
		Archetyped archetypeDetails = new Archetyped(new ArchetypeID(
				"openehr-ehr_rm-Composition.physical_examination.v2"), "1.0");
		return new Composition(id, node, name, archetypeDetails, null, null,
				null, content, TestTerminologyAccess.ENGLISH, context(),
				provider(), category, territory(), ts);
	}

	private Section section() throws Exception {
		DvText name = new DvText("test section", lang, encoding, ts);
		List<ContentItem> items = new ArrayList<ContentItem>();
		items.add(observation());
		return new Section("at0000", name, items);
	}

}
