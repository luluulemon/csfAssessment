package vttp2022.csf.assessment.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class UploadService {
    
    @Autowired
    private AmazonS3 s3Client;

    public String upload(InputStream is, String ObjId) throws IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "lulu");
        userData.put("uploadTime", (new Date()).toString());

        // Metadata of the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setUserMetadata(userData);

        // Create a put request
        PutObjectRequest putReq = new PutObjectRequest("lufirstbucket", 
                                            "assessment/%s".formatted(ObjId), 
                                            is, metadata);

        // To allow public access
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putReq);

        return ObjId;
    }
}
