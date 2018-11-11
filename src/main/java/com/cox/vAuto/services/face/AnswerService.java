package com.cox.vAuto.services.face;

import com.cox.vAuto.models.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public interface AnswerService {

    String getdatasetId ();
    VehicleIdModel getvehicleIds (String datasetId);
    VehicleModel getvehicleInfo (String datasetId, Integer vehicleId) ;
    CompletableFuture<DealerIdModel>  getdealerInfo(String datasetId,Integer dealerId);
    ResponseModel postAnswer(String dataset,PostAnswerModel postAnswerModel);
    Map<Integer, Dealers> getUniqueDealerListMap (List <CompletableFuture<DealerIdModel>> listOfDealerId,List<VehicleModel> listOfvehicles)throws InterruptedException,ExecutionException;

}
