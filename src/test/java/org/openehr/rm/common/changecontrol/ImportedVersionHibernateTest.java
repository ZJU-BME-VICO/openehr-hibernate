package org.openehr.rm.common.changecontrol;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.common.directory.Folder;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ImportedVersionHibernateTest extends
		ChangeControlHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {

		UIDBasedID uid = new HierObjectID("1.4.7.23.4.7.23");

		Folder fd = new Folder(uid, "at0000", text("folder name"), null, null,
				null, null, null, null);

		OriginalVersion<Locatable> ov = new OriginalVersion<Locatable>(
				new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::1"), null, fd,
				lifeCycleState("complete"), audit("1-4-3-5-2",
						"comitter's name", "changeTypeCode",
						"2006-07-01T13:22:55"), contribution(
						"1.4.4.5::1.2.840.114.1.2.2::2", "path/morePath"),
				null, null, null, TestTerminologyService.getInstance());
		ImportedVersion<Locatable> expect = new ImportedVersion<Locatable>(ov,
				audit("adminc.nhs.uk", "comitter's name", "changeTypeCode",
						"2006-07-01T15:00:09"), contribution(
						"1.4.4.15::1.2.840.114.1.2.2::1", "path/morePath"),
				null);

		Session session = sessionFactory.getCurrentSession();
		session.save(expect);
		session.flush();
		session.clear();
		ImportedVersion<Locatable> actual = (ImportedVersion<Locatable>) session
				.load(ImportedVersion.class, expect.getMappingId());
		assertThat(actual.equals(expect), is(true));

	}

	private DvText text(String value) {
		return new DvText(value, TestCodeSetAccess.ENGLISH,
				TestCodeSetAccess.LATIN_1, TestTerminologyService.getInstance());
	}

}
