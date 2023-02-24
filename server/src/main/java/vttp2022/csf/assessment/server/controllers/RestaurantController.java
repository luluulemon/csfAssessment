package vttp2022.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.repositories.MapCache;
import vttp2022.csf.assessment.server.services.ConvertingService;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
public class RestaurantController {
    
    @Autowired
    private RestaurantService restSvc;

    @Autowired
    private ConvertingService convertSvc;

    @Autowired
    private MapCache mapCache;

    @GetMapping(path="/api/{cuisine}/restaurants")
    @ResponseBody
    public ResponseEntity<String> getRestByCuisine(@PathVariable String cuisine){
        String cuisineSlash = cuisine.replace("_", "/");    // add back underscore
        return ResponseEntity.ok( restSvc.getRestaurantsByCuisine(cuisineSlash).toString() );
    }


    @GetMapping(path="/api/{id}")
    @ResponseBody
    public ResponseEntity<String> getRestById(@PathVariable String id){
        Optional<Restaurant> restOp = restSvc.getRestaurant(id);
        if(restOp.isEmpty())
            return null;

        Restaurant rest = restOp.get();
        System.out.println("Check Add: " +rest.getAddress());
        System.out.println("Check name: " +rest.getName());
        System.out.println("Check Cuisine: " +rest.getCuisine());
        System.out.println("Check Coordinates: " +rest.getCoordinates());
        System.out.println("Check id: " + rest.getRestaurantId());
        
        mapCache.getMap(rest.getCoordinates(), id);
        return ResponseEntity.ok( convertSvc.toJson(rest).toString() );
    }

    @PostMapping(path="/api/comments", consumes="application/json")
    @ResponseBody
    public ResponseEntity<String> postComment(@RequestBody String value){
        JsonReader reader = Json.createReader(new StringReader(value));
        JsonObject commentJson = reader.readObject(); 
        Comment newComment = convertSvc.createComment(commentJson);

        System.out.println("Check new COmment: " + newComment.getText());
        restSvc.addComment(newComment);

        return ResponseEntity
            .status(HttpStatusCode.valueOf(201))
            .body( Json.createObjectBuilder()
                    .add("message", "comment posted")
                    .build()
                    .toString());
    }

}
