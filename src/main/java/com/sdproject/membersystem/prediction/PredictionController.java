package com.sdproject.membersystem.prediction;

import com.sdproject.membersystem.registration.RegistrationRequest;
import com.sdproject.membersystem.registration.RegistrationService;
import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping(path="api/prediction")
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping()
    public ReturnData prediction(@RequestBody PredictedData request){
//        String line="";
        ReturnData returnData=new ReturnData(null,null,null,null);
        try{
            returnData=predictionService.startPredict(request);
        } catch (Exception e){
            e.printStackTrace();
//            line="prediction error occur";
        }
//        System.out.println(line);
        return returnData;
    }
}
