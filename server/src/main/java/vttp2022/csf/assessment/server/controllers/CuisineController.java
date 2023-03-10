package vttp2022.csf.assessment.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
public class CuisineController {
    
    @Autowired
    private RestaurantService restSvc;

    @GetMapping(path ="/api/cuisines")
    @ResponseBody
    public ResponseEntity<String> getCuisines(){
        return ResponseEntity.ok( restSvc.getCuisines().toString() );
    }
}
