package com.cox.vAuto.services.impl;

import com.cox.vAuto.exception.NotFoundException;
import com.cox.vAuto.models.*;
import com.cox.vAuto.repository.face.AnswerRepository;
import com.cox.vAuto.services.face.AnswerService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

     @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public String getdatasetId() {
        return answerRepository.getdatasetId();
    }


    @Override
    public VehicleIdModel getvehicleIds(String datasetID) {

        return  answerRepository.getvehicleIds(datasetID);
    }


    @Override

    public VehicleModel getvehicleInfo(String datasetId, Integer vehicleId) {

        return answerRepository.getvehicleInfo(datasetId,vehicleId);
    }


    @Override
   @Async("asyncExecutor")
    public CompletableFuture<DealerIdModel> getdealerInfo(String datasetId, Integer dealerId) {
         return answerRepository.getdealerInfo(datasetId,dealerId);
    }


    @Override
    public ResponseModel postAnswer(String dataset, PostAnswerModel postAnswerModel) {

        return answerRepository.postAnswer(dataset,postAnswerModel);
    }

    @Override
    public  Map<Integer, Dealers> getUniqueDealerListMap (List<CompletableFuture<DealerIdModel>> listOfDealerId, List<VehicleModel> listOfvehicles)throws InterruptedException,ExecutionException
    {
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
                        new Dealers(vehicle.getDealerId(),"", new
                                ArrayList<>()));
            }
            Dealers dealers = uniqueDealerListMap.get(vehicle.getDealerId());
            dealers.getVehicles().add(new VehicleInfoModel(vehicle.getVehicleId(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel()));
        }

        Dealers dealersModel = null;
        for (CompletableFuture<DealerIdModel> dealerIdModel: listOfDealerId) {
            DealerIdModel dealersId = dealerIdModel.get();
            if (uniqueDealerListMap.containsKey(dealersId.getDealerId())) {
                dealersModel = uniqueDealerListMap.get(dealersId.getDealerId());
                dealersModel.setName(dealersId.getName());
            }
        }
        return uniqueDealerListMap;
    }

}
