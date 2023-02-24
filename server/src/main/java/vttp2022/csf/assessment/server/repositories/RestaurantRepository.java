package vttp2022.csf.assessment.server.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.ConcatArrays;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.ConvertingService;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Repository
public class RestaurantRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ConvertingService convertSvc;

	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  db.restaurants.distinct("cuisine")
	public List<String> getCuisines() {
		// Implmementation in here
		return
		mongoTemplate.findDistinct(new Query(), "cuisine", "restaurants", String.class);
	}


	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
		// db.restaurants.find(
		//	{ cuisine: 'Afghan'},
		//	{ name: 1}	)
	public List<Document> getRestaurantsByCuisine(String cuisine) {
		// Get Obj with Name & Obj Id
		Query query = Query.query(Criteria.where("cuisine").is(cuisine));
		query.fields().include("name");

		return mongoTemplate.find(query,Document.class,"restaurants");
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	// db.restaurants.aggregate(
//    	[  	{ $match: {  _id: ObjectId('63f811d8c5605f1c8f6a92a6')} },
//    		{ $project: { 
// 	   						_id: 0,
// 	   						restaurant_id: "$restaurant_id",
// 	   						name: "$name",
// 	   						cuisine: "$cuisine",
// 	   						address: { $concat: [ "$address.building", ", ", "$address.street", ", ", "$address.zipcode", ", ", "$borough" ] },
// 	   						coordinates: "$address.coord",
// 			   			}
//    		}
// 		] )
	public Optional<Restaurant> getRestaurant(String id) {
		// Implmementation in here
		ObjectId restId = new ObjectId(id);

		MatchOperation match = Aggregation.match( Criteria.where("_id").is(restId) );
		ProjectionOperation project = 
			Aggregation.project("restaurant_id", "name", "cuisine")
			.and(	StringOperators.Concat.valueOf("address.building").concat(", ")
					.concatValueOf("address.street").concat(", ")
					.concatValueOf("address.zipcode").concat(", ")
					.concatValueOf("borough")	)
			.as("address")
			.and("address.coord").as("coordinates")
			.andExclude("_id");



		Aggregation pipeline = Aggregation.newAggregation(match, project);
		AggregationResults<Document> results = mongoTemplate.aggregate(
					pipeline, "restaurants", Document.class);

		Document doc = results.iterator().next();


		Restaurant rest = convertSvc.create( doc );
		return Optional.of(rest);
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		Document commentDoc = Document.parse(convertSvc.toJson(comment).toString());
		Document newDoc = mongoTemplate.insert( commentDoc, "comments" );
	}
	
	// You may add other methods to this class

}
