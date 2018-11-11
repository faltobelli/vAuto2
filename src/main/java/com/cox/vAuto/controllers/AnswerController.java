package com.cox.vAuto.controllers;

import com.cox.vAuto.models.*;
import com.cox.vAuto.services.face.AnswerService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.List;


@Api(value = "Answer-Controller", description = "One run to return Success")
@RestController
@RequestMapping("/api")
public class AnswerController {

    private AnswerService answerService;


    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "/getvauto", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> getvAutoInfo() throws InterruptedException, ExecutionException {

        VehicleModel  vehiclesInfo;
        List <CompletableFuture<DealerIdModel>> listOfDealerId = new ArrayList<>();
        List<VehicleModel> listOfvehicles = new ArrayList<>();

        //get datasetId
        String datasetId= answerService.getdatasetId();
        //get all vehicleIds
        VehicleIdModel vehicleIds=answerService.getvehicleIds(datasetId);

        Set<Integer> seenDealers = new HashSet<>();
        for(int i=0;i<vehicleIds.getVehicleIds().size();i++) {
            //get vehicleinfo
            vehiclesInfo = answerService.getvehicleInfo(datasetId, vehicleIds.getVehicleIds().get(i));
            listOfvehicles.add(vehiclesInfo);
            if (!seenDealers.contains(vehiclesInfo.getDealerId())) {
                listOfDealerId.add(answerService.getdealerInfo(datasetId, vehiclesInfo.getDealerId()));
                seenDealers.add(vehiclesInfo.getDealerId());
            }

        }
        //wait for list to be completed.
        listOfDealerId.stream().map(CompletableFuture::join).collect(Collectors.toList());

        // Build up multiMap
        Map<Integer, Dealers> uniqueDealerListMap = new HashMap<>();
        Multimap<Integer, VehicleInfoModel> listMultimap = ArrayListMultimap.create();
        for (VehicleModel vehicle : listOfvehicles) {
            listMultimap.put(vehicle.getDealerId(),
                    new VehicleInfoModel(vehicle.getVehicleId(),
                            vehicle.getYear(),
                            vehicle.getMake(),
                            vehicle.getModel()));

            if (!uniqueDealerListMap.containsKey(vehicle.getDealerId())) {
                uniqueDealerListMap.put(vehicle.getDealerId(),
                        new Dealers(vehicle.getDealerId(),"", new ArrayList<>()));
            }
            Dealers dealers = uniqueDealerListMap.get(vehicle.getDealerId());
            dealers.getVehicles().add(new VehicleInfoModel(vehicle.getVehicleId(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel()));
        }

        // List<Dealers> dealersList=new ArrayList<>();
        Dealers dealersModel = null;
        for (CompletableFuture<DealerIdModel> dealerIdModel: listOfDealerId) {
            DealerIdModel dealersId = dealerIdModel.get();
            if (uniqueDealerListMap.containsKey(dealersId.getDealerId())) {
                dealersModel = uniqueDealerListMap.get(dealersId.getDealerId());
                dealersModel.setName(dealersId.getName());
            }
        }

        //use Dealers list to PostModel
        PostAnswerModel postModel =new PostAnswerModel(new ArrayList<>(uniqueDealerListMap.values()));

        return new ResponseEntity<>(answerService.postAnswer(datasetId,postModel), HttpStatus.OK);
    }

}
