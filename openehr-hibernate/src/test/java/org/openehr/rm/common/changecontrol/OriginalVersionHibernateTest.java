package org.openehr.rm.common.changecontrol;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.common.directory.Folder;
import org.openehr.rm.common.generic.Attestation;
import org.openehr.rm.common.generic.AuditDetails;
import org.openehr.rm.common.generic.PartyIdentified;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.datatypes.text.TestCodePhrase;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.LocatableRef;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.PartyRef;
import org.openehr.rm.support.identification.TestTerminologyID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class OriginalVersionHibernateTest extends AbstractHibernateTest {

	@Test
	public void testSaveLoad() throws Exception {

		Folder fd = new Folder(new HierObjectID("1.4.7.23.4.7.23"), "at0000",
				text("folder name"), null, null, null, null, null, null);

		ObjectVersionID uid = new ObjectVersionID(
				"1.4.4.5::1.2.840.114.1.2.2::1");
		CodePhrase codePhrase = new CodePhrase(TestTerminologyID.SNOMEDCT,
				"revisionCode");
		DvCodedText codedText = new DvCodedText("complete",
				TestCodePhrase.ENGLISH, TestCodePhrase.LATIN_1, codePhrase,
				TestTerminologyService.getInstance());
		PartyIdentified pi = new PartyIdentified(new PartyRef(new HierObjectID(
				"1-2-3-4-5"), "PARTY"), "committer name", null);
		AuditDetails audit1 = new AuditDetails("12.3.4.5", pi, new DvDateTime(
				"2006-05-01T10:10:00"), codedText, null,
				TestTerminologyService.getInstance());
		ObjectRef lr = new LocatableRef(new ObjectVersionID(
				"1.23.51.66::1.2.840.114.1.2.2::2"), "LOCAL", "CONTRIBUTION",
				null);
		Set<ObjectVersionID> otherUids = new HashSet<ObjectVersionID>();
		otherUids
				.add(new ObjectVersionID("1.4.14.5::1.2.840.114.1.2.2::4.2.2"));
		OriginalVersion<Locatable> expect = new OriginalVersion<Locatable>(uid,
				null, fd, codedText, audit1, lr, null, otherUids, null, TestTerminologyService.getInstance());

		Session session = sessionFactory.getCurrentSession();
		session.save(expect);
		session.flush();
		session.clear();
		OriginalVersion<Locatable> actual = (OriginalVersion<Locatable>) session
				.load(OriginalVersion.class, expect.getMappingId());
		if (actual.getAttestations().size() == 0) {
			actual.setAttestations(null);
		}
		if (actual.getOtherInputVersionUids().size() == 0) {
			actual.setOtherInputVersionUids(null);
		}
		assertThat(actual.getCommitAudit().equals(expect.getCommitAudit()), is(true));
		assertThat(actual.getUid(), is(expect.getUid()));
		assertThat(actual.getPrecedingVersionUid(), is(expect.getPrecedingVersionUid()));
		assertThat(actual.getContribution(), is(expect.getContribution()));
		assertThat(actual.getData(), is(expect.getData()));
		assertThat(actual.getLifecycleState(), is(expect.getLifecycleState()));
		assertThat(actual.getSignature(), is(expect.getSignature()));
		assertThat(actual.getMappingId(), is(expect.getMappingId()));
		assertThat(actual.equals(expect), is(true));
		Set<ObjectVersionID> otherInputVersionUids1 = actual.getOtherInputVersionUids();
		Set<ObjectVersionID> otherInputVersionUids2 = expect.getOtherInputVersionUids();
		assertThat(actual.getOtherInputVersionUids().equals(expect.getOtherInputVersionUids()), is(true));
		
		
	}

	private DvText text(String value) {
		return new DvText(value, TestCodeSetAccess.ENGLISH,
				TestCodeSetAccess.LATIN_1, TestTerminologyService.getInstance());
	}

}
