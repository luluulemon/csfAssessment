package vttp2022.csf.assessment.server.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.services.UploadService;

@Repository
public class MapCache {

	@Autowired
	private UploadService uploadSvc;

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public void getMap(LatLng coords, String id) {
		// Implmementation in here
		String endPoint = "http://map.chuklee.com/map";
		String url = UriComponentsBuilder.fromUriString(endPoint).queryParam("lat", coords.getLatitude())
                    .queryParam("lng", coords.getLongitude()).toUriString(); 
		System.out.println(url);
		RequestEntity req = RequestEntity.get(url)
				.header("Accept", "image/png").build();
		RestTemplate template = new RestTemplate();
		ResponseEntity<byte[]> resp = template.exchange(req, byte[].class);
		
		try{
			InputStream is = new ByteArrayInputStream(resp.getBody());

			uploadSvc.upload(is, id);
			}catch(IOException e){	e.printStackTrace();  }// to handle this
		
	}

	// You may add other methods to this class

}
