package org.openehr.rm.common.changecontrol;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.common.directory.Folder;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.support.identification.HierObjectID;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.support.identification.VersionTreeID;
import org.openehr.rm.support.terminology.TestCodeSetAccess;
import org.openehr.rm.support.terminology.TestTerminologyAccess;
import org.openehr.rm.support.terminology.TestTerminologyService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class VersionedObjectHibernateTest extends
		ChangeControlHibernateTestBase {

	@Test
	public void testSaveLoad() throws Exception {

		UIDBasedID uid = new HierObjectID("1.4.7.23.4.7.23");

		Folder fd = new Folder(uid, "at0000", text("folder name"), null, null,
				null, null, null, null);

		String firstCSID = "1.2.40.14.1.2.2";
		String time = "2006-07-14T14:33:29";
		VersionedObject<Locatable> repository = repository(fd, firstCSID, time);
		
		ObjectVersionID fVUid = new ObjectVersionID(repository.getUid().root()
				.toString(), firstCSID, "1");
		
		Session session = sessionFactory.getCurrentSession();
		session.save(repository);
		VersionedObject<Locatable> a1 = (VersionedObject<Locatable>) session
				.load(VersionedObject.class, repository.getMappingId());
		assertThat(a1, is(repository));

		// test count total number of versions
		assertEquals("versionCount", 1, repository.versionCount());

		// test has version id
		assertTrue(repository.hasVersionID(fVUid));

		// test isOriginalVersion
		assertTrue(repository.isOriginalVersion(fVUid));

		// test get latest version
		Version<Locatable> version = repository.latestVersion();
		assertEquals("latestVersion", fd, version.getData());

		// test get latest trunk version
		version = repository.latestTrunkVersion();
		assertEquals("latestTrunkVersion", fd, version.getData());

		// test get latest trunk version lifecyclestate
		assertEquals(
				new DvCodedText("creation", TestTerminologyAccess.CREATION),
				repository.latestTrunkLifeCycleSate());

		// test has version at time
		assertTrue(repository.hasVersionAtTime(version.getCommitAudit()
				.getTimeCommitted()));

		// test all versions
		assertEquals(1, repository.allVersions().size());
		assertTrue(repository.allVersions().contains(version));

		// test all version ids
		assertEquals(1, repository.allVersionIDs().size());
		assertTrue(repository.allVersionIDs().contains(fVUid));

		// test versionWithID
		version = repository.versionWithID(fVUid);
		assertEquals("versionWithID", fd, version.getData());

		// test versionAtTime
		version = repository.versionAtTime(new DvDateTime(time));
		assertEquals("versionWithID", fd, version.getData());

	}

	private DvText text(String value) {
		return new DvText(value, TestCodeSetAccess.ENGLISH,
				TestCodeSetAccess.LATIN_1, TestTerminologyService.getInstance());
	}

	// test repository
	VersionedObject<Locatable> repository(Locatable firstData,
			String creatingSysID, String time) throws Exception {
		HierObjectID id = new HierObjectID("1-2-5-2-4");
		ObjectRef ehrRef = new ObjectRef(new HierObjectID("ehrdomain::1"),
				"LOCAL", "EHR");
		ObjectVersionID vUid = new ObjectVersionID(id.root(), new HierObjectID(
				creatingSysID), new VersionTreeID("1"));
		DvCodedText changeType = new DvCodedText("creation",
				TestTerminologyAccess.CREATION);
		return new VersionedObject<Locatable>(id, ehrRef, new DvDateTime(time),
				vUid, firstData, changeType, audit("1-5-7-7-1", "Yinsu",
						"changeTypeCode", "2006-07-14T14:33:29"), contribution(
						"1-6-2-2-4::1.2.40.14::1", "path"),
				"comitter's signature", TestTerminologyService.getInstance());
	}

}
