package org.eamcode.polarreportanalyzer.controller;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TrainingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrainingService trainingService;


    TrainingRequest trainingRequestTwo;
    TrainingRequest trainingRequestOne;
    TrainingResponse trainingResponseOne;
    TrainingResponse trainingResponseTwo;
    @BeforeEach
    void setUp() {



        trainingRequestOne = new TrainingRequest(
                null,
                "Interval training 4x4 (3)",
                "C:/polar-data/running-2026-04-30.csv",
                7,
                LocalDateTime.of(2026, 4, 30, 17, 59, 30),
                "2026-04-30",
                "17:59:30",
                "RUNNING",
                "00:45:00",
                "145",
                "10.2",
                "7.65",
                "174",
                List.of()
        );

        trainingRequestTwo = new TrainingRequest(
                null,
                "Interval training 4x4 (3)",
                "C:/polar-data/running-2026-04-30.csv",
                7,
                LocalDateTime.of(2026, 4, 30, 17, 59, 30),
                "2026-04-30",
                "17:59:30",
                "RUNNING",
                "00:45:00",
                "145",
                "10.2",
                "7.65",
                "174",
                List.of()
        );



        trainingResponseOne = new TrainingResponse(
                null,
                "RUNNING: 2026-04-30 17:59:30",
                "Interval training 4x4 (3)",
                "C:/polar-data/running-2026-04-30.csv",
                7,
                LocalDateTime.of(2026, 4, 30, 17, 59, 30),
                "2026-04-30",
                "17:59:30",
                "RUNNING",
                "00:45:00",
                "145",
                "10.2",
                "7.65",
                "174",
                "85",
                "82",
                List.of()
        );

        trainingResponseTwo = new TrainingResponse(
                null,
                "RUNNING: 2026-01-01 01:01:01",
                "Easy Run 70 min",
                null,
                5,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    public void shouldCreateTrainingAndReturnResponse() throws Exception {
        given(trainingService.createTraining(ArgumentMatchers.any(TrainingRequest.class)))
                .willReturn(trainingResponseOne);

        ResultActions result = mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingRequestOne)));


        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}