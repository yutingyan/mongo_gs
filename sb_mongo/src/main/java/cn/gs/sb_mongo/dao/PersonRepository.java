package cn.gs.sb_mongo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.gs.sb_mongo.domain.Person;

//MongoRepository<Person, String>
//Person为仓库保管的bean类
//String为该bean的唯一标识的类型
public interface PersonRepository extends MongoRepository<Person, String> {
	
	//方法名查询
	Person findByName(String name);
	
	//@Query查询
	//?0指第一个参数，即age
	@Query("{'age':?0}")
	List<Person> withQueryFindByAge(int age);

}
