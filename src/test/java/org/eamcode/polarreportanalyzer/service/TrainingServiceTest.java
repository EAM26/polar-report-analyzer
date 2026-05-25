package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.service.imp.DefaultDataPointService;
import org.eamcode.polarreportanalyzer.util.CsvReader;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private MetaDataService metaDataService;

    @Mock
    private DefaultDataPointService dataPointService;

    @Mock
    private CsvReader csvReader;

    @InjectMocks
    private TrainingService trainingService;

    Training trainingOne;
    Training trainingTwo;
    TrainingRequest trainingRequestOne;
    TrainingRequest trainingRequestTwo;
    TrainingResponse trainingResponseOne;
    TrainingResponse trainingResponseTwo;

    List<String[]> allDataRows = List.of(new String[0], new String[0]);

    @BeforeEach
    void setup() {
        trainingOne = Training.builder()
                .name("RUNNING: 2026-04-30 17:59:30")
                .description("Interval training 4x4 (3)")
                .pathToReport("C:/polar-data/running-2026-04-30.csv")
                .rpe(7)
                .createdAt(LocalDateTime.of(2026, 4, 30, 17, 59, 30))
                .date("2026-04-30")
                .startTime("17:59:30")
                .sport("RUNNING")
                .duration("00:45:00")
                .hrAvg("145")
                .speedAvg("10.2")
                .totalDistance("7.65")
                .cadenceAvg("174")
                .ascent("85")
                .descent("82")
                .build();

        trainingTwo = Training.builder()
                .name("RUNNING: 2026-01-01 01:01:01")
                .description("Easy Run 70 min")
                .rpe(5)
                .build();

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
    public void shouldUpdateAndReturnTrainingResponse() {

        //Arrange
        Long id = 1L;
        when(trainingRepository.findById(id)).thenReturn(Optional.of(trainingOne));
        when(modelMapper.updateTrainingFromRequest(trainingRequestTwo, trainingOne)).thenReturn(trainingTwo);
        when(trainingRepository.save(trainingTwo)).thenReturn(trainingTwo);
        when(modelMapper.mapTrainingToResponse(Mockito.any(Training.class))).thenReturn(trainingResponseTwo);

        //Act
        TrainingResponse result = trainingService.updateTraining(id, trainingRequestTwo);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(trainingResponseTwo);

        verify(modelMapper).updateTrainingFromRequest(trainingRequestTwo, trainingOne);
        verify(trainingRepository).save(trainingTwo);
        verify(modelMapper).mapTrainingToResponse(trainingTwo);

    }

    @Test
    public void shouldDeleteTraining() {
        //Arrange
        Long id = 1L;

        //Act
        trainingService.deleteTraining(id);

        //Assert
        verify(trainingRepository).deleteById(id);
    }

    @Test
    public void shouldReturnListWithTwoTrainings() {

        //Arrange
        when(trainingRepository.findAll()).thenReturn(List.of(trainingOne, trainingTwo));
        when(modelMapper.mapTrainingToResponse(trainingOne)).thenReturn(trainingResponseOne);
        when(modelMapper.mapTrainingToResponse(trainingTwo)).thenReturn(trainingResponseTwo);
        //Act
        List<TrainingResponse> result = trainingService.getAllTrainings();

        //Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).contains(trainingResponseOne);
        assertThat(result).contains(trainingResponseTwo);
    }

    @Test
    public void shouldReturnOneTrainingById() {

        //Arrange
        when(trainingRepository.findById(trainingOne.getId())).thenReturn(Optional.of(trainingOne));
        when(modelMapper.mapTrainingToResponse(trainingOne)).thenReturn(trainingResponseOne);

        //Act
        TrainingResponse result = trainingService.getTrainingById(trainingOne.getId());

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(trainingResponseOne.name());
        assertThat(result.equals(trainingResponseOne)).isTrue();

        verify(trainingRepository).findById(trainingOne.getId());
        verify(modelMapper).mapTrainingToResponse(trainingOne);
    }

    @Test
    public void shouldThrowRecordNotFoundExceptionWhenTrainingDoesNotExistInCreate() {

        //Arrange
        Long id = 1L;
        when(trainingRepository.findById(id)).thenReturn(Optional.empty());

        //Act and Assert
        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () ->  trainingService.getTrainingById(id));

        assertThat(ex.getMessage()).isEqualTo("No training found with id: 1");
    }

    @Test
    public void shouldThrowRecordNotFoundExceptionWhenTrainingDoesNotExistInUpdate() {

        //Arrange
        Long id = 1L;
        when(trainingRepository.findById(id)).thenReturn(Optional.empty());

        //Act and Assert
        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () ->  trainingService.updateTraining(id, trainingRequestOne));

        assertThat(ex.getMessage()).isEqualTo("No training found with id: 1");
    }

    @Test
    public void shouldCreateAndReturnTrainingResponse() {

        //Arrange
        when(modelMapper.mapToTrainingEntity(trainingRequestOne)).thenReturn(trainingOne);
        when(csvReader.readDataRows(trainingOne.getPathToReport())).thenReturn(allDataRows);
        when(modelMapper.mapTrainingToResponse(Mockito.any(Training.class))).thenReturn(trainingResponseOne);

        //Act
        TrainingResponse result = trainingService.createTraining(trainingRequestOne);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(trainingResponseOne);

        verify(modelMapper).mapToTrainingEntity(trainingRequestTwo);
        verify(csvReader).readDataRows(trainingOne.getPathToReport());
        verify(metaDataService).setTrainingFields(trainingOne, allDataRows.get(1));
        verify(trainingRepository).save(trainingOne);
        verify(modelMapper).mapTrainingToResponse(trainingOne);

    }


}