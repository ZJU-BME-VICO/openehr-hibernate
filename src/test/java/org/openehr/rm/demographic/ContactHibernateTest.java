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
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.datatypes.quantity.DvInterval;
import org.openehr.rm.datatypes.quantity.datetime.DvDate;

/**
 * EventContextTest
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ContactHibernateTest extends DemographicHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {	

        DvInterval<DvDate> timeValidity = new DvInterval<DvDate>(
                date("2005-01-01"), date("2005-12-31"));
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address(oid("address.trial"), "at0000",
                text("post address"), null, null, null, null, 
                itemSingle("post address details")));
        addresses.add(new Address(oid("1.2.4.5.7.33.7"), "at0000",
                text("email address"), null, null, null, null, 
                itemSingle("email address details")));

        Contact ct = new Contact(null, "at0000", text("contact name"),
                null, null, null, null, timeValidity, addresses);

		Session session = sessionFactory.getCurrentSession();
		session.save(ct);
		Contact a1 = (Contact)session.load(Contact.class, ct.getMappingId());
		assertThat(a1, is(ct));

    }

}
