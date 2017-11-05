package com.test;

import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.dao.MongoDbDao;
import com.mongodb.client.result.UpdateResult;

public class mongoDbTest {

	@Before
	public void before() {
		MongoDbDao.connect("demo", "mycoll", "192.168.191.1", 27017);
	}

	@Test
	public void testInsert() {
		Document document = new Document();
		document.append("name", "test1").append("sex", "0");
		MongoDbDao.insert(document);
	}

	@Test
	public void testFindAll() {
		List<Document> results = MongoDbDao.queryAll();
		for (Document doc : results) {
			System.out.println(doc.toJson());
		}
	}

	@Test
	public void testFindBy() {
		Document filter = new Document();
		filter.append("name", "louis");
		List<Document> results = MongoDbDao.queryBy(filter);
		for (Document doc : results) {
			System.out.println(1 + "\t" + doc.toJson());
		}
	}

	@Test
	public void testUpdateOne() {
		Document filter = new Document();
		filter.append("name", "sex");
		// 注意update文档里要包含"$set"字段
		Document update = new Document();
		update.append("$set", new Document("name", "louis2"));
		UpdateResult result = MongoDbDao.updateOne(filter, update);
		System.out.println("matched count = " + result.getMatchedCount());
	}

	@Test
	public void testUpdateMany() {
		Document filter = new Document();
		filter.append("name", "louis");

		// 注意update文档里要包含"$set"字段
		Document update = new Document();
		update.append("$set", new Document("name", "male"));
		UpdateResult result = MongoDbDao.updateMany(filter, update);
		System.out.println("matched count = " + result.getMatchedCount());
	}

	@Test
	public void testReplace() {
		Document filter = new Document();
		filter.append("name", "louis");

		// 注意：更新文档时，不需要使用"$set"
		Document replacement = new Document();
		replacement.append("value", 123);
		MongoDbDao.replace(filter, replacement);
	}

	@Test
	public void testDeleteOne() {
		Document filter = new Document();
		filter.append("value", 123);
		MongoDbDao.deleteOne(filter);
	}

	@Test
	public void testDeleteMany() {
		Document filter = new Document();
		filter.append("name", "louis");
		MongoDbDao.deleteMany(filter);
	}

}
