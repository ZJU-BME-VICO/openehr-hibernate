package org.openehr.rm.common.generic;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.PartyRef;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class AuditDetailsCreateHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {	

		TerminologyService termServ = TestTerminologyService.getInstance();	
		
		CodePhrase lang = new CodePhrase("ISO_639-1", "en");
	    CodePhrase encoding = new CodePhrase("IANA_character-sets", "UTF-8");	    
		CodePhrase creationCode = new CodePhrase("openehr", "249");		
		DvCodedText codedText = new DvCodedText("creation", 
				lang, encoding, creationCode, termServ);
		PartyIdentified pi = new PartyIdentified(new PartyRef(new HierObjectID(
				"1-2-3-4-5"), "PARTY"), "committer name", null);
		
		AuditDetails audit = new AuditDetails("12.3.4.5", pi, new DvDateTime(
		"2007-08-14T10:10:00"), codedText, null, termServ);
		
		Session session = sessionFactory.getCurrentSession();
		session.save(audit);
		AuditDetails a1 = (AuditDetails)session.load(AuditDetails.class, audit.getMappingId());
		assertThat(a1, is(audit));
		
	}
	
}
