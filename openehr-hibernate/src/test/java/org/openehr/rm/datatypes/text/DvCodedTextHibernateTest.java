package org.openehr.rm.datatypes.text;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;

public class DvCodedTextHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {
        CodePhrase definingCode = new CodePhrase("test terms", "12345");
        DvCodedText ct = new DvCodedText("coded text", definingCode);
 		Session session = sessionFactory.getCurrentSession();
 		session.save(ct);
 		DvCodedText codedText = (DvCodedText)session.load(DvCodedText.class, ct.getMappingId());
 		assertThat(codedText, is(ct)); 	
	}

}
