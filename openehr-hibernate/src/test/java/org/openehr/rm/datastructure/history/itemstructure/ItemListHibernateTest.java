/*
 * component:   "openEHR Reference Implementation"
 * description: "Class ItemListTest"
 * keywords:    "unit test"
 *
 * author:      "Rong Chen <rong@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2004 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/BRANCHES/RM-1.0-update/libraries/src/test/org/openehr/rm/datastructure/itemstructure/ItemListTest.java $"
 * revision:    "$LastChangedRevision: 50 $"
 * last_change: "$LastChangedDate: 2006-08-10 13:21:46 +0200 (Thu, 10 Aug 2006) $"
 */
/**
 * ItemListTest
 *
 * @author Rong Chen
 * @version 1.0 
 */
package org.openehr.rm.datastructure.history.itemstructure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.datastructure.DataStructureHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemList;
import org.openehr.rm.datastructure.itemstructure.representation.Element;

import java.util.*;

public class ItemListHibernateTest extends DataStructureHibernateTestBase {

	@Test
	public void testSaveLoad() {

        // save element in the array for comparison
		List<Locatable> elements = new ArrayList<Locatable>();

        // elements
        for (int i = 0; i < NAMES.length; i++) {
            elements.add(element(NAMES[ i ], VALUES[ i ]));
        }
        ItemList itemList = new ItemList(null, "at001", text(NAME), null, null, null, 
                null, elements);

		Session session = sessionFactory.getCurrentSession();
		session.save(itemList);
		ItemList il = (ItemList)session.load(ItemList.class, itemList.getMappingId());
		assertThat(il, is(itemList));
		
	}

    /* static fields */
    private static final String NAME = "BP protocol";
    private static final String[] NAMES = {
        "device", "cuff", "position"
    };
    private static final String[] VALUES = {
        "sphygmomanometer", "wide", "seated"
    };
}
/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is ItemListTest.java
 *
 *  The Initial Developer of the Original Code is Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */