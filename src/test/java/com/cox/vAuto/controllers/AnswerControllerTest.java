package com.cox.vAuto.controllers;

import com.cox.vAuto.exception.NotFoundException;
import com.cox.vAuto.models.*;
import com.cox.vAuto.services.face.AnswerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.TRUE;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerControllerTest {
    private static final String DATASET_ID = "9mjAHFtH1gg";
    private static final Integer MOCK_DEALERID1 = 1;
    private static final Integer MOCK_DEALERID2 = 2;
    private static final Integer MOCK_DEALERID3 = 3;

    @MockBean
    private AnswerService answerService;

    private AnswerController answerController;
    private List<Dealers> mockDealers;
    private PostAnswerModel mockPostAnswer;
    private PostAnswerModel mockPostAnswerFailed;
    private List<Dealers> mockDealersFailed;

    // responses
    ResponseModel success;
    ResponseModel failed;

    DealerIdModel dealerIdModel1() {return new DealerIdModel(1,"Bob's Cars");};
    DealerIdModel dealerIdModel2() {return new DealerIdModel(2,"House of Wheels");};
    DealerIdModel dealerIdModel3() {return new DealerIdModel(3,"Doug's Doozies");};

    @Before
    public void setUp() throws Exception {

        answerController =new AnswerController(answerService);

        List<Integer> vehicleIdList = new ArrayList<>();
        vehicleIdList.add(1); vehicleIdList.add(2); vehicleIdList.add(3);
        vehicleIdList.add(4); vehicleIdList.add(5); vehicleIdList.add(6);
        vehicleIdList.add(7); vehicleIdList.add(8); vehicleIdList.add(9);
        VehicleIdModel vehicleIds= new VehicleIdModel();
        vehicleIds.setVehicleIds(vehicleIdList);

        when(answerService.getvehicleIds(DATASET_ID)).thenReturn(vehicleIds);
        // really need one of these for each vehicle in the list
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(1))).thenReturn(new VehicleModel(1,2016,"Honda","Accord", MOCK_DEALERID1));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(2))).thenReturn(new VehicleModel(2,2014,"Ford","F150", MOCK_DEALERID1));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(3))).thenReturn(new VehicleModel(3,2004,"MINI","Cooper", MOCK_DEALERID1));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(4))).thenReturn(new VehicleModel(4,2013,"Mitsubishi","Gallant", MOCK_DEALERID2));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(5))).thenReturn(new VehicleModel(5,2009,"Ford","Ford", MOCK_DEALERID2));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(6))).thenReturn(new VehicleModel(6,2016,"Kia","Soul", MOCK_DEALERID2));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(7))).thenReturn(new VehicleModel(7,2016,"Bentley","Mulsanne", MOCK_DEALERID3));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(8))).thenReturn(new VehicleModel(8,2012,"Nissan","Altima", MOCK_DEALERID3));
        when(answerService.getvehicleInfo(eq(DATASET_ID), eq(9))).thenReturn(new VehicleModel(9,1979,"Cheverolet","Corvette", MOCK_DEALERID3));

        CompletableFuture<DealerIdModel> dealer1 = supplyAsync(this::dealerIdModel1);
        CompletableFuture<DealerIdModel> dealer2 = supplyAsync(this::dealerIdModel2);
        CompletableFuture<DealerIdModel> dealer3 = supplyAsync(this::dealerIdModel3);
        when(answerService.getdealerInfo(eq(DATASET_ID), eq(MOCK_DEALERID1))).thenReturn(dealer1);
        when(answerService.getdealerInfo(eq(DATASET_ID), eq(MOCK_DEALERID2))).thenReturn(dealer2);
        when(answerService.getdealerInfo(eq(DATASET_ID), eq(MOCK_DEALERID3))).thenReturn(dealer3);

        success=new ResponseModel(TRUE,"congratulations",25438);
        failed=new ResponseModel(TRUE,"Failed",25438);

    }

    @Test
    //@Ignore
    public void getvAutoInfo_test() throws InterruptedException, ExecutionException {
        // Given
        when(answerService.getdatasetId()).thenReturn(DATASET_ID);
        when(answerService.postAnswer(eq(DATASET_ID), any())).thenReturn(success);

        // When
        ResponseEntity<ResponseModel> answer = answerController.getvAutoInfo();

        // then
        assertNotNull(answer);
        assertTrue(answer.getStatusCode() == HttpStatus.OK);
    }

    @Test(expected = NotFoundException.class)
    public void getvAutoInfo_test_notFound_test() throws InterruptedException, ExecutionException{

        doThrow(new NotFoundException(String.format("Dataset ID  was not found.")))
                .when(answerService)
                .getdatasetId();
        answerService.getdatasetId();

        fail();

    }


}
