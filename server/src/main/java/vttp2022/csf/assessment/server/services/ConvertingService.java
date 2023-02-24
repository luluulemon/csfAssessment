package vttp2022.csf.assessment.server.services;

import org.bson.Document;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.Restaurant;

@Service
public class ConvertingService {
    
    public Restaurant create(Document doc){     // converts aggreated Doc to Restaurant Object
		Restaurant rest = new Restaurant();
		rest.setRestaurantId(doc.getString("restaurant_id"));
		rest.setName(doc.getString("name"));
		rest.setCuisine(doc.getString("cuisine"));
        rest.setAddress(doc.getString("address"));
		
		LatLng coord = new LatLng();
		coord.setLatitude(
            Float.valueOf(
                doc.getList("coordinates", Double.class)
                    .get(1)
                        .toString()
                    ) );
		coord.setLongitude(
            Float.valueOf(
                doc.getList("coordinates", Double.class)
                    .get(0)
                        .toString()
                    ) );

        System.out.println("Check coord lang: " + coord.getLatitude());
        System.out.println("Check coord long: " + coord.getLongitude());
		rest.setCoordinates(coord);
		return rest;
	}

    public JsonObject toJson(Restaurant rest){
        return
        Json.createObjectBuilder().add("namd", rest.getName())
                                .add("cusisine", rest.getCuisine())
                                .add("address", rest.getAddress())
                                .add("restaurantId", rest.getRestaurantId())
                                .build();
    }

    public Comment createComment(JsonObject commentJson){   // create Comment Obj from angular form input
        Comment comment = new Comment();
        comment.setName(commentJson.getString("name"));
        comment.setRating( Integer.parseInt(  commentJson.getString("rating")   ) );
        comment.setText(commentJson.getString("text"));
        comment.setRestaurantId(commentJson.getString("restaurantId"));
        return comment;
    }

    public JsonObject toJson(Comment comment){
        return
        Json.createObjectBuilder().add("name", comment.getName())
                                .add("rating", comment.getRating())
                                .add("comment", comment.getText())
                                .add("restId", comment.getRestaurantId())
                                .build();
    }


}
