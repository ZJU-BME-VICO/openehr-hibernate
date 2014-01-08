/*
 * component:   "openEHR Reference Implementation"
 * description: "Class ItemSingleTest"
 * keywords:    "unit test"
 *
 * author:      "Rong Chen <rong@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2004 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/BRANCHES/RM-1.0-update/libraries/src/test/org/openehr/rm/datastructure/itemstructure/ItemSingleTest.java $"
 * revision:    "$LastChangedRevision: 50 $"
 * last_change: "$LastChangedDate: 2006-08-10 13:21:46 +0200 (Thu, 10 Aug 2006) $"
 */
/**
 * ItemSingleTest
 *
 * @author Rong Chen
 * @version 1.0 
 */
package org.openehr.rm.datastructure.history.itemstructure;

import java.util.ArrayList;
import java.util.List;

import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.datastructure.itemstructure.representation.Cluster;
import org.openehr.rm.datastructure.itemstructure.representation.Element;
import org.openehr.rm.datastructure.itemstructure.representation.Item;
import org.openehr.rm.datatypes.quantity.DvQuantity;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.DvText;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.datastructure.DataStructureHibernateTestBase;
import org.openehr.rm.datastructure.itemstructure.ItemTree;
import org.openehr.rm.support.measurement.MeasurementService;
import org.openehr.rm.support.measurement.TestMeasurementService;

public class ItemTreeHibernateTest extends DataStructureHibernateTestBase {

	@Test
	public void testSaveLoad() {

        // sample
    	sample = new Element("at0001", new DvText("sample"), 
    			new DvCodedText("serum", new CodePhrase("terminology", "111")));
    	
    	// lipid studies
    	totalCholesterol = new Element("at0002", new DvText("total cholesterol"),
    			new DvQuantity("mmol/L", 6.1, measureServ));
    	
    	ldlCholesterol = new Element("at0003", new DvText("LDL cholesterol"),
    			new DvQuantity("mmol/L", 0.9, measureServ));
    	
    	hdlCholesterol = new Element("at0004", new DvText("HDL cholesterol"),
    			new DvQuantity("mmol/L", 5.2, measureServ));
    	
    	List<Locatable> items = new ArrayList<Locatable>();
    	items.add(totalCholesterol);
    	items.add(ldlCholesterol);
    	items.add(hdlCholesterol);
    	lipidStudies = new Cluster("at0005", new DvText("lipid studies"), items);
    	
    	// comment
    	comment = new Element("at0006", new DvText("comment"),
    			new DvText("high cardiac risk"));
    	
    	items = new ArrayList<Locatable>();
    	items.add(sample);
    	items.add(lipidStudies);
    	items.add(comment);
    	itemTree = new ItemTree("at0007", new DvText("biochemstry result"), items);

		Session session = sessionFactory.getCurrentSession();
		session.save(itemTree);
		ItemTree itemSingle1 = (ItemTree)session.load(ItemTree.class, itemTree.getMappingId());
		assertThat(itemSingle1, is(itemTree));
		
	}
    
    /* fields */
    private ItemTree itemTree;
    private Element sample;
    private Element totalCholesterol;
    private Element ldlCholesterol;
    private Element hdlCholesterol;
    private Element comment;
    private Cluster lipidStudies;
    private MeasurementService measureServ = TestMeasurementService.getInstance();
    
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
 *  The Original Code is ItemSingleTest.java
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