package vttp2022.csf.assessment.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
public class RestaurantController {
    
    @Autowired
    private RestaurantService restSvc;

    @GetMapping(path="/api/{cuisine}/restaurants")
    @ResponseBody
    public ResponseEntity<String> getRestByCuisine(@PathVariable String cuisine){
        String cuisineSlash = cuisine.replace("_", "/");    // add back underscore
        return ResponseEntity.ok( restSvc.getRestaurantsByCuisine(cuisineSlash).toString() );
    }
}
