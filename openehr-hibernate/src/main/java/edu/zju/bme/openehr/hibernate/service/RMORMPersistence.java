package edu.zju.bme.openehr.hibernate.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface RMORMPersistence {

	@WebMethod
	int insert(List<String> dadls, List<String> adls);

	@WebMethod
	int delete(String aql);

	@WebMethod
	List<String> select(String aql);

	@WebMethod
	List<String> selectPersonByObjectUids(List<String> objectUids);

}
