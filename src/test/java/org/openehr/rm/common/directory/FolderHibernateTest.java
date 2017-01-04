package org.openehr.rm.common.directory;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FolderHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {		

    	UIDBasedID uid = new HierObjectID("1.4.7.23.4.7.23");

        // verify a bug fix in constructor
    	Folder fd = new Folder(uid, "at0000", text("folder name"), null,
                null, null, null, null, null);
        
		Session session = sessionFactory.getCurrentSession();
		session.save(fd);
		Folder a1 = (Folder)session.load(Folder.class, fd.getMappingId());
		assertThat(a1, is(fd));
	}

    private DvText text(String value) {
        return new DvText(value, TestCodeSetAccess.ENGLISH, TestCodeSetAccess.LATIN_1,
                TestTerminologyService.getInstance());
    }
    
}
