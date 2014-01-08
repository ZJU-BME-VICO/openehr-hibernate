package edu.zju.bme.openehr.hibernate.dao;

import java.util.List;

public interface RMORMPersistenceDao {

	int insert(List<String> dadls, List<String> adls);

	int delete(String adl);
	
	List<String> selectPersonByObjectUids(List<String> objectUids);
//
//	List<CoarseNodePathEntity> selectCoarseNodePathByIds(List<Integer> ids);
//	
////	List<CoarseNodePathEntity> selectCoarseNodePathByPathValues(Map<String, String> pathValues);
//
//	List<CoarseNodePathEntity> selectCoarseNodePathByPathValues(List<String> paths, List<String> values);

}
