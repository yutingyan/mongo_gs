package cn.gs.sb_mongo.config;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//存在疑问
//@Configuration或者@Service两个注解二选一，有没有@Bean都可以
@Configuration
//@Service
public class MyConfig {
	
	@Value("${spring.data.mongodb.host}")
	private String host;
	@Value("${spring.data.mongodb.port}")
	private int port;
	@Value("${spring.data.mongodb.database}")
	private String database;
	
//	@Bean
	public MongoClient mongoClient(){
		MongoClient mongoClient = new MongoClient(new ServerAddress(host, port));
		return mongoClient;
	}
	
//	@Bean
	public MongoDatabase mongoDatabase(){
		MongoDatabase mongoDatabase = mongoClient().getDatabase(database);
		return mongoDatabase;
	}
	
//	//Parameter 0 of method mongoCollection in cn.gs.sb_mongo.template.MyTemplate required a bean of type 'java.lang.String' that could not be found
//	@Bean
//	public MongoCollection<Document> mongoCollection(String collectionName){
//		MongoCollection<Document> mongoCollection = mongoDatabase().getCollection(collectionName);
//		return mongoCollection;
//	}
//	@Bean
	public MongoCollection<Document> mongoCollection(){
		MongoCollection<Document> mongoCollection = mongoDatabase().getCollection("gs");
		return mongoCollection;
	}
	
//	@Bean
	public MongoDbFactory mongoDbFactory(){
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient(), database);
		return mongoDbFactory;
	}
	
	@Bean
	public MongoTemplate mongoTemplate(){
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}

}
