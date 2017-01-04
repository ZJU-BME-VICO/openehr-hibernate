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
package org.openehr.rm.demographic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.datatypes.quantity.DvInterval;
import org.openehr.rm.datatypes.quantity.datetime.DvDate;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.support.identification.LocatableRef;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.UIDBasedID;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class PersonHibernateTest extends DemographicHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

		UIDBasedID uid = oid("1.7.8.4");
		DvText name = text("person name");
		ItemStructure details = itemSingle("person details");

		Set<PartyIdentity> identities = new HashSet<PartyIdentity>();
		identities.add(new PartyIdentity(null, "at0000",
				text(Agent.LEGAL_IDENTITY), null, null, null, null,
				itemSingle(" identity value")));
		Archetyped archetypeDetails = new Archetyped(new ArchetypeID(
				"openehr-dm_rm-person.person.v1"), "v1.0");

		Set<Contact> contacts = new HashSet<Contact>();
		DvInterval<DvDate> timeValidity = new DvInterval<DvDate>(
				date("2005-01-01"), date("2005-12-31"));
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(new Address(oid("address.ad2"), "at0000",
				text("telecom address"), null, null, null, null,
				itemSingle("telecom addresss details")));
		Contact contact = new Contact(null, "at0000", text("contact meaning"),
				null, null, null, null, timeValidity, addresses);
		contacts.add(contact);

		Set<PartyRelationship> relationships = new HashSet<PartyRelationship>();
		timeValidity = new DvInterval<DvDate>(date("1960-12-25"), null);
		ObjectRef source = new ObjectRef(uid, "LOCAL", "PARTY");
		ObjectRef target = new ObjectRef(oid("1.7.34.8"), "LOCAL", "PARTY");
		PartyRelationship relation = new PartyRelationship(oid("1.3.6.7.3"),
				"at0000", text("mother"), null, null, null, null,
				itemSingle("mother to son"), timeValidity, source, target);
		relationships.add(relation);

		Set<LocatableRef> reverseRelationships = new HashSet<LocatableRef>();
		reverseRelationships.add(new LocatableRef(
				ovid("1.4.4.5::1.2.840.114.1.2.2::1"), "LOCAL", "PARTY", null));

		List<Capability> capabilities = new ArrayList<Capability>();
		capabilities.add(new Capability(null, "at0000",
				text("capability meaning"), null, null, null, null,
				timeValidity, itemSingle("capability credentials")));
		Set<DvText> languages = new HashSet<DvText>();
		languages.add(text("swedish"));

		// null roles
		Person ps = new Person(uid, "at0000", name, archetypeDetails, null, null,
				identities, contacts, relationships, reverseRelationships,
				details, null, languages);

		Session session = sessionFactory.getCurrentSession();
		session.save(ps);
		Person a1 = (Person)session.load(Person.class, ps.getMappingId());
		assertThat(a1, is(ps));

    }

}
