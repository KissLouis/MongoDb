package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

/**
 * 
 * @Title: MongoDbDao
 * @Description:
 * @author Louis
 * @date 2017年9月24日
 */
public class MongoDbDao {

	private static MongoCollection<Document> collection;

	/**
	 * 连接数据库
	 * 
	 * @title: connect
	 * @description:
	 * @param databaseName
	 *            数据库名称
	 * @param collectionName
	 *            集合名称
	 * @param hostName
	 *            主机名
	 * @param port
	 *            端口名
	 */
	public static void connect(String databaseName, String collectionName,
			String hostName, int port) {
		MongoClient client = new MongoClient(hostName, port);
		MongoDatabase db = client.getDatabase(databaseName);
		collection = db.getCollection(collectionName);
		System.out.println(collection);
	}

	/**
	 * 插入一个文档
	 * 
	 * @title: insert
	 * @description:
	 * @param document
	 *            文档
	 */
	public static void insert(Document document) {
		collection.insertOne(document);
	}

	/**
	 * 查询所有文档
	 * 
	 * @title: queryAll
	 * @description:
	 * @return
	 */
	public static List<Document> queryAll() {
		List<Document> results = new ArrayList<Document>();
		FindIterable<Document> iterables = collection.find();
		MongoCursor<Document> cursor = iterables.iterator();
		while (cursor.hasNext()) {
			results.add(cursor.next());
		}
		return results;
	}

	/**
	 * 根据条件查询
	 * 
	 * @title: queryBy
	 * @description:
	 * @param filter
	 *            查询条件//注意Bson的几个实现类，BasicDBObject,
	 *            BsonDocument,BsonDocumentWrapper, CommandResult, Document,
	 *            RawBsonDocument
	 * @return
	 */
	public static List<Document> queryBy(Bson filter) {
		List<Document> results = new ArrayList<Document>();
		FindIterable<Document> iterables = collection.find(filter);
		MongoCursor<Document> cursor = iterables.iterator();
		while (cursor.hasNext()) {
			results.add(cursor.next());
		}
		return results;
	}

	/**
	 * 更新查询到的第一个
	 * 
	 * @title: updateOne
	 * @description:
	 * @param filter
	 *            查询条件
	 * @param update
	 *            更新文档
	 * @return
	 */
	public static UpdateResult updateOne(Bson filter, Bson update) {
		UpdateResult result = collection.updateOne(filter, update);
		return result;
	}

	/**
	 * 更新查询到的所有的文档
	 * 
	 * @title: updateMany
	 * @description:
	 * @param filter
	 *            查询条件
	 * @param update
	 *            更新文档
	 * @return
	 */
	public static UpdateResult updateMany(Bson filter, Bson update) {
		UpdateResult result = collection.updateMany(filter, update);
		return result;
	}

	/**
	 * 更新一个文档, 结果是replacement是新文档，老文档完全被替换
	 * 
	 * @title: replace
	 * @description:
	 * @param filter
	 *            查询条件
	 * @param replacement
	 *            更新文档
	 */
	public static void replace(Bson filter, Document replacement) {
		collection.replaceOne(filter, replacement);
	}

	/**
	 * 根据条件删除一个文档
	 * 
	 * @title: deleteOne
	 * @description:
	 * @param filter
	 */
	public static void deleteOne(Bson filter) {
		collection.deleteOne(filter);
	}

	/**
	 * 根据条件删除多个文档
	 * @title: deleteMany
	 * @description: 
	 * @param filter
	 */
	public static void deleteMany(Bson filter) {
		collection.deleteMany(filter);
	}

}
