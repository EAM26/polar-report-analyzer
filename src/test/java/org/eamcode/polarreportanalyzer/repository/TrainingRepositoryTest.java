package org.eamcode.polarreportanalyzer.repository;

import org.eamcode.polarreportanalyzer.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    private Training trainingOne;
    private Training trainingTwo;
    private Training trainingCycling;

    @BeforeEach
    void setUp() {
        trainingOne = Training.builder()
                .name("RUNNING: 2026-04-30 17:59:30")
                .description("Interval training 4x4 (3)")
                .rpe(7)
                .build();

        trainingTwo = Training.builder()
                .name("RUNNING: 2026-01-01 01:01:01")
                .description("Easy Run 70 min")
                .rpe(5)
                .build();

        trainingCycling = Training.builder()
                .name("CYCLING: 2026-02-02 01:01:01")
                .description("mountainbike tour")
                .rpe(6)
                .build();
    }

    @Test
    public void shouldSaveTraining() {

        //Act
        Training savedTraining = trainingRepository.save(trainingOne);

        //Assert
        assertThat(savedTraining).isNotNull();
        assertThat(savedTraining.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldReturnTwoTrainings() {

        //Arrange
        Training savedTrainingOne = trainingRepository.save(trainingOne);
        Training savedTrainingTwo = trainingRepository.save(trainingTwo);

        //Act
        List<Training> savedTrainings = trainingRepository.findAll();

        //Assert
        assertThat(savedTrainings).hasSize(2);
        assertThat(savedTrainings).contains(savedTrainingOne);
        assertThat(savedTrainings).contains(savedTrainingTwo);
    }

    @Test
    public void shouldReturnOneTrainingById() {

        //Arrange
        Training savedTrainingOne = trainingRepository.save(trainingOne);

        //Act
        Optional<Training> returnedTraining = trainingRepository.findById(savedTrainingOne.getId());

        //Assert
        assertThat(returnedTraining).isPresent();
        assertThat(returnedTraining.get()).isEqualTo(trainingOne);
    }

    @Test
    public void shouldReturnTwoTrainingsBySportRunning() {

        //Arrange
        trainingOne.setSport("running");
        trainingRepository.save(trainingOne);
        trainingTwo.setSport("RuNnInG");
        trainingRepository.save(trainingTwo);
        trainingCycling.setSport("cycling");
        trainingRepository.save(trainingCycling);

        //Act
//        Optional<Training> returnedTraining = trainingRepository.findById(savedTrainingOne.getId());
        List<Training> returnedTrainings = trainingRepository.findBySportIgnoreCase("running");
        //Assert
        assertThat(returnedTrainings).hasSize(2);
        assertThat(returnedTrainings).contains(trainingOne);
        assertThat(returnedTrainings).contains(trainingTwo);
        assertThat(returnedTrainings).doesNotContain(trainingCycling);
    }
}