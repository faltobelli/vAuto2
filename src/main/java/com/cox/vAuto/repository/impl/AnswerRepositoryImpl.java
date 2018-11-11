package com.cox.vAuto.repository.impl;

import com.cox.vAuto.exception.NotFoundException;
import com.cox.vAuto.models.*;
import com.cox.vAuto.repository.face.AnswerRepository;
import com.cox.vAuto.services.face.AnswerService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
@Repository
public class AnswerRepositoryImpl implements AnswerRepository {


    @Override
    public String getdatasetId() {

            final String uri = "http://vautointerview.azurewebsites.net/api/datasetId";
            String datasetID="";
            RestTemplate restTemplate = new RestTemplate();

            DatasetModel datasetId;

            try {
                datasetId = restTemplate.getForObject(uri, DatasetModel.class);
            }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new RuntimeException("Cannot Retrieve DatasetId");
            }

            return datasetId.getDatasetId().toString();
    }


        @Override
        public VehicleIdModel getvehicleIds(String datasetID) {

            final String uri = "http://vautointerview.azurewebsites.net/api/"+datasetID+"/vehicles";

            RestTemplate restTemplate = new RestTemplate();

            VehicleIdModel vehicleIds;
            try {
                vehicleIds   = restTemplate.getForObject(uri,VehicleIdModel.class );
            }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new NotFoundException("Cannot Retrieve VehicleIds");
            }

            return vehicleIds;
        }


        @Override

        public VehicleModel getvehicleInfo(String datasetId, Integer vehicleId) {

            final String uri = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/vehicles/"+vehicleId;

            RestTemplate restTemplate = new RestTemplate();

            VehicleModel vehicleInfo;
            try {
                vehicleInfo   = restTemplate.getForObject(uri,VehicleModel.class );


            }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new NotFoundException("Cannot Retrieve VehicleInfo");
            }

            return vehicleInfo;
        }



        @Override
        @Async("asyncExecutor")
        public CompletableFuture<DealerIdModel> getdealerInfo(String datasetId, Integer dealerId) {


            final String uri = "http://vautointerview.azurewebsites.net/api/"+datasetId+"/dealers/"+dealerId;

            RestTemplate restTemplate = new RestTemplate();

            DealerIdModel dealerInfo;
            try {
                dealerInfo   = restTemplate.getForObject(uri,DealerIdModel.class );

            }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new NotFoundException("Cannot Retrieve VehicleInfo");
            }

            return CompletableFuture.completedFuture(dealerInfo);
        }



        @Override
        public ResponseModel postAnswer(String dataset, PostAnswerModel postAnswerModel) {

            final String uri = "http://vautointerview.azurewebsites.net/api/"+dataset+"/answer";

            RestTemplate restTemplate = new RestTemplate();

            ResponseModel response;
            try {
                response   = restTemplate.postForObject(uri,postAnswerModel,ResponseModel.class );
            }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new NotFoundException("Cannot verify Answer");
            }

            return response;
        }

    }






