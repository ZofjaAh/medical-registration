package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.database.entity.MedicalRecordEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moj.project.util.EntityFixtures.someMedicalRecord1;
import static org.assertj.core.api.Assertions.assertThat;


@AllArgsConstructor(onConstructor = @__(@Autowired))
class MedicalRecordJpaRepositoryTest extends AbstractJpa{
    private MedicalRecordJpaRepository medicalRecordJpaRepository;
    @Test
    void thatMedicalRecordCanBeSavedCorrectly() {
          medicalRecordJpaRepository.saveAndFlush(someMedicalRecord1());


        // when
        List<MedicalRecordEntity> availableMedicalRecords = medicalRecordJpaRepository.findAll();

        // then
        assertThat(availableMedicalRecords).hasSize(2);
    }

}