package org.openehr.rm.common.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.TestCodePhrase;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.PartyRef;
import org.openehr.rm.support.identification.TestTerminologyID;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class RevisionHistoryHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() {	

        PartyRef pr = new PartyRef(new HierObjectID("1-2-3-4-5"), "PARTY");
        PartyIdentified pi = new PartyIdentified(pr, "party name", null);
        CodePhrase codePhrase =
                new CodePhrase(TestTerminologyID.SNOMEDCT, "revisionCode");
        DvCodedText codedText = new DvCodedText("code value", TestCodePhrase.ENGLISH,
                TestCodePhrase.LATIN_1, codePhrase,
                TestTerminologyService.getInstance());
        AuditDetails audit1 = new AuditDetails("12.3.4.5", pi, 
                new DvDateTime("2006-05-01T10:10:00"), codedText, null,
                TestTerminologyService.getInstance());
        AuditDetails audit2 = new AuditDetails("20.3.33.55", pi, 
                new DvDateTime("2006-06-01T10:10:00"), codedText, null,
                TestTerminologyService.getInstance());
        List<AuditDetails> audits1 = new ArrayList<AuditDetails>();
        audits1.add(audit1);
        List<AuditDetails> audits2 = new ArrayList<AuditDetails>();
        audits2.add(audit2);
        RevisionHistoryItem rhi1 = new RevisionHistoryItem(audits1, 
                new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::123::1"));
        RevisionHistoryItem rhi2 = new RevisionHistoryItem(audits2, 
                new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::123::2"));
        List<RevisionHistoryItem> rhiList = new ArrayList<RevisionHistoryItem>();
        rhiList.add(rhi1);
        rhiList.add(rhi2);
        RevisionHistory rh = new RevisionHistory(rhiList);
        
		Session session = sessionFactory.getCurrentSession();
		session.save(rh);
		RevisionHistory a1 = (RevisionHistory)session.load(RevisionHistory.class, rh.getMappingId());
		assertThat(a1, is(rh));
		
	}
	
}
