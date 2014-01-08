package org.openehr.rm.datatypes.text;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.openehr.rm.AbstractHibernateTest;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.rm.support.terminology.TestTerminologyService;

public class DvTextHibernateTest extends AbstractHibernateTest {

	@Test
    public void testSaveLoad() {
		
		List<DvText> textList = new ArrayList<DvText>();
		
    	TerminologyService ts = TestTerminologyService.getInstance();

    	CodePhrase lang = new CodePhrase("ISO_639-1", "en");
    	CodePhrase charset = new CodePhrase("IANA_character-sets", "UTF-8");
    	
		textList.add(new DvText("value", null, null, null, null, null, null));
		textList.add(new DvText("value"));        
		textList.add(new DvText("test", lang, null, ts));
		textList.add(new DvText("test", null, charset, ts));
		textList.add(new DvText("test", lang, charset, ts));
		
 		Session session = sessionFactory.getCurrentSession();
 		for (int i = 0; i < textList.size(); i++) {
			session.save(textList.get(i));
		}
 		
 		for (int i = 0; i < textList.size(); i++) {
 			DvText text = (DvText)session.load(DvText.class, textList.get(i).getMappingId());
 	        assertThat(text, is(textList.get(i))); 		
		}
 		
    }

}
