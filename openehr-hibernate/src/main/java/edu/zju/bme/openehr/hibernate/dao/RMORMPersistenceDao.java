package edu.zju.bme.openehr.hibernate.dao;

import java.util.List;

public interface RMORMPersistenceDao {

	int insert(List<String> dadls, List<String> adls);

	int delete(String aql);

	List<String> select(String aql);
	
	List<String> selectPersonByObjectUids(List<String> objectUids);

}
