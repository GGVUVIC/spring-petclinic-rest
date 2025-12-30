package org.springframework.samples.petclinic.util;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityUtilsTests {

    @Test
    void shouldReturnEntityWhenFound() {
        Pet pet = new Pet();
        pet.setId(1);

        List<Pet> pets = new ArrayList<>();
        pets.add(pet);

        Pet result = EntityUtils.getById(pets, Pet.class, 1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(){{
            setId(1);
        }});

        assertThrows(ObjectRetrievalFailureException.class,
                () -> EntityUtils.getById(pets, Pet.class, 999));
    }
}
