package edu.zju.bme.openehr.hibernate.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface RMORMPersistence {

	@WebMethod
	int insert(List<String> dadls, List<String> adls);

	@WebMethod
	int delete(String adl);

	@WebMethod
	List<String> selectPersonByObjectUids(List<String> objectUids);
//
////	@WebMethod
////	List<CoarseNodePathEntity> selectCoarseNodePathByPathValues(
////			@XmlJavaTypeAdapter(PathValueMapAdapter.class) Map<String, String> pathValues);
//
//	@WebMethod
//	List<CoarseNodePathEntity> selectCoarseNodePathByPathValues(List<String> paths, List<String> values);

}
