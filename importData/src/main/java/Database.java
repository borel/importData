package main.java;

import java.net.UnknownHostException;

import main.ressources.Ressources;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class Database {

	private DB db = null;
	private DBCollection collectionMessage;

	public void connection() throws UnknownHostException {

		Mongo mongo;

		// connect to mongoDB, ip and port number
		mongo = new Mongo(Ressources.HOST, Ressources.PORT);

		// get database from MongoDB,
		// if database doesn't exists, mongoDB will create it automatically
		db = mongo.getDB(Ressources.DATABASE_NAME);

		// take the collection from the database
		collectionMessage = db.getCollection(Ressources.COLLECTION_MESSAGE);

	}

	public void insertMessage(MessageData messageData) {
		// insertion of the message
		BasicDBObject document = messageData.toBasicDBObject();

		// save it into collection
		collectionMessage.insert(document);

		//
		// // query it
		// DBCursor cursor = collection.find();
		//
		// // loop over the cursor and print it retrieved result
		// while (cursor.hasNext()) {
		// System.out.println(cursor.next());
		// }
	}

}
