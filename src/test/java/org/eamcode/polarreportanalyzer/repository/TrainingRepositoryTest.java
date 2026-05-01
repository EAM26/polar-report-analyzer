package org.eamcode.polarreportanalyzer.repository;

import org.assertj.core.api.Assertions;
import org.eamcode.polarreportanalyzer.model.Training;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Test
    public void Repo_Save_ReturnSavedTraining() {

        //Arrange
        Training training = Training.builder()
                .name("RUNNING: 2026-04-30 17:59:30")
                .description("Interval training 4x4 (3)")
                .rpe(7)
                .build();

        //Act
        Training savedTraining = trainingRepository.save(training);

        //Assert
        Assertions.assertThat(savedTraining).isNotNull();
        Assertions.assertThat(savedTraining.getId()).isGreaterThan(0);
    }
}