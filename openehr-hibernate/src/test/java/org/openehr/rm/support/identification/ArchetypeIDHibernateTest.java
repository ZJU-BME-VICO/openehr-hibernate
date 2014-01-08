package org.openehr.rm.support.identification;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import static org.junit.Assert.*;

public class ArchetypeIDHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
		Session session = sessionFactory.getCurrentSession();
		List<ArchetypeID> termList = new ArrayList<ArchetypeID>();
		for (int i = 0; i < STRING_VALUE.length; i++) {
			ArchetypeID term = new ArchetypeID(STRING_VALUE[i]);
			termList.add(term);
			session.save(term);
		}

		for (int i = 0; i < STRING_VALUE.length; i++) {
			ArchetypeID o = (ArchetypeID)session.load(ArchetypeID.class,
					termList.get(i).getMappingId());
			if (o != null) {
				assertArchetypeID(o, i);
			}
		}
	}


    // assert content of archetype id
    private void assertArchetypeID(ArchetypeID aid, int i) {
        assertEquals("value", STRING_VALUE[ i ], aid.getValue());
        assertEquals("contextID", null, aid.contextID());
        assertEquals("localID", STRING_VALUE[ i ], aid.localID());

        assertEquals("rmOriginator", SECTIONS[ i ][ 0 ],
                aid.rmOriginator());
        assertEquals("rmName", SECTIONS[ i ][ 1 ], aid.rmName());
        assertEquals("rmEntity", SECTIONS[ i ][ 2 ],
                aid.rmEntity());
        assertEquals("conceptName", SECTIONS[ i ][ 3 ],
                aid.conceptName());
        
        List<String> list = new ArrayList<String>();
        if(SECTIONS[ i ][ 4 ] != null) {
        	list.add(SECTIONS[ i ][ 4 ]);
        }
        assertEquals("specialisation", list, aid.specialisation());

        assertEquals("qualifiedRmEntity", AXES[ i ][ 0 ],
                aid.qualifiedRmEntity());
        assertEquals("domainConcept", AXES[ i ][ 1 ],
                aid.domainConcept());
        assertEquals("versionID", AXES[ i ][ 2 ],
                aid.versionID());
    }

    private static String[] STRING_VALUE = {
        "openehr-ehr_rm-section.physical_examination.v2",
        "openehr-ehr_rm-section.physical_examination-prenatal.v1",
        "hl7-rim-act.progress_note.v1",
        "openehr-ehr_rm-ENTRY.progress_note-naturopathy.draft"
    };

    private static String[][] SECTIONS = {
        {"openehr", "ehr_rm", "section", "physical_examination",
         null, "v2"},
        {"openehr", "ehr_rm", "section", "physical_examination",
         "prenatal", "v1"},
        {"hl7", "rim", "act", "progress_note", null, "v1"},
        {"openehr", "ehr_rm", "ENTRY", "progress_note", "naturopathy", "draft"}
    };

    private static String[][] AXES = {
        {"openehr-ehr_rm-section", "physical_examination", "v2"},
        {"openehr-ehr_rm-section", "physical_examination-prenatal",
         "v1"},
        {"hl7-rim-act", "progress_note", "v1"},
        {"openehr-ehr_rm-ENTRY", "progress_note-naturopathy", "draft"}
    };
    
}
