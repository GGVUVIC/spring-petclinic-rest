package org.springframework.samples.petclinic.service.clinicService;

import jakarta.persistence.EntityManager;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.clinicService.AbstractClinicServiceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Visit;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Visit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ClinicServiceJpaTests extends AbstractClinicServiceTests {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private EntityManager entityManager;

    @Override
    void clearCache() {
        entityManager.clear();
    }

    @Test
    void shouldCreateVisitWhenDateIsInFuture() throws DataAccessException {
        Pet pet7 = this.clinicService.findPetById(7); // assignem un pet existent

        Visit visit = new Visit();
        visit.setPet(pet7);
        visit.setDate(LocalDate.now().plusDays(1));
        visit.setDescription("Future visit");

        assertDoesNotThrow(() -> clinicService.saveVisit(visit));
        assertNotNull(visit.getId(), "Visit should have an ID after being saved");
    }

    @Test
    void shouldCreateVisitWhenDateIsToday() throws DataAccessException {
        Pet pet7 = this.clinicService.findPetById(7);

        Visit visit = new Visit();
        visit.setPet(pet7);
        visit.setDate(LocalDate.now());
        visit.setDescription("Today visit");

        assertDoesNotThrow(() -> clinicService.saveVisit(visit));
        assertNotNull(visit.getId(), "Visit should have an ID after being saved");
    }

    @Test
    void shouldFailWhenVisitDateIsInThePast() {
        Pet pet7 = this.clinicService.findPetById(7);

        Visit visit = new Visit();
        visit.setPet(pet7);
        visit.setDate(LocalDate.now().minusDays(1));
        visit.setDescription("Past visit");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clinicService.saveVisit(visit));

        assertEquals("Visit date cannot be in the past", exception.getMessage());
    }
}
