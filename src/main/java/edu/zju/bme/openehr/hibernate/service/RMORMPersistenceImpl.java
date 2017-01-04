package edu.zju.bme.openehr.hibernate.service;

import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.openehr.hibernate.dao.RMORMPersistenceDao;

@WebService(endpointInterface = "edu.zju.bme.openehr.hibernate.service.RMORMPersistence")
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@Component("RMORMPersistence")
@Transactional
public class RMORMPersistenceImpl implements RMORMPersistence {

	@Resource(name="RMORMPersistenceDao")
	private RMORMPersistenceDao persistenceDao;

	@Override
	public int insert(List<String> dadls, List<String> adls) {
		
		return persistenceDao.insert(dadls, adls);

	}

	@Override
	public int delete(String aql) {
		
		return persistenceDao.delete(aql);

	}

	@Override
	public List<String> select(String aql) {
		
		return persistenceDao.select(aql);

	}

	@Override
	public List<String> selectPersonByObjectUids(List<String> objectUids) {
		
		return persistenceDao.selectPersonByObjectUids(objectUids);

	}

}
