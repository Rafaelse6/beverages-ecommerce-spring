package com.rafaelsantos.beveragesecommerce.services;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.BeverageMinDTO;
import com.rafaelsantos.beveragesecommerce.entities.Category;
import com.rafaelsantos.beveragesecommerce.entities.DTO.BeverageDTO;
import com.rafaelsantos.beveragesecommerce.factories.BeverageFactory;
import com.rafaelsantos.beveragesecommerce.repositories.BeverageRepository;
import com.rafaelsantos.beveragesecommerce.repositories.CategoryRepository;
import com.rafaelsantos.beveragesecommerce.services.exceptions.DatabaseException;
import com.rafaelsantos.beveragesecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class BeverageServiceTests {

    @InjectMocks
    private BeverageService service;

    @Mock
    private BeverageRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingBeverageId, nonExistingBeverageId, dependentBeverageId;
    private String beverageName;
    private Beverage beverage;
    private BeverageDTO beverageDTO;

    private Category category;

    private PageImpl<Beverage> page;

    @BeforeEach
    void setUp() throws Exception{
        existingBeverageId  = 1L;
        nonExistingBeverageId = 2L;
        dependentBeverageId = 3L;

        beverageName = "Heineken";
        beverage = BeverageFactory.createBeverate(beverageName);
        beverageDTO = new BeverageDTO(beverage);

        category = new Category();
        category.setId(1L);
        category.setName("Alcoholic");

        page = new PageImpl<>(List.of(beverage));

        Mockito.when(repository.findById(existingBeverageId)).thenReturn(Optional.of(beverage));
        Mockito.when(repository.findById(nonExistingBeverageId)).thenReturn(Optional.empty());

        Mockito.when(repository.searchByName(any(), any())).thenReturn(page);

        Mockito.when(repository.save(any())).thenReturn(beverage);
        Mockito.when(categoryRepository.findById(any())).thenReturn(Optional.of(category));

        Mockito.when(repository.getReferenceById(existingBeverageId)).thenReturn(beverage);
        Mockito.when(repository.getReferenceById(nonExistingBeverageId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(repository.existsById(existingBeverageId)).thenReturn(true);
        Mockito.when(repository.existsById(dependentBeverageId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingBeverageId)).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(existingBeverageId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentBeverageId);
    }

    @Test
    public void findByIdShouldReturnBeverageDTOWhenIdExists(){
        BeverageDTO result = service.findById(existingBeverageId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(),existingBeverageId);
        Assertions.assertEquals(result.getName(), beverage.getName());
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingBeverageId));
    }

    @Test
    public void findAllShouldReturnPageBeverageMinDTO(){
        Pageable pageable = PageRequest.of(0,12);
        String name = "Heineken";

        Page<BeverageMinDTO> result = service.findAll(name, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), 1);
        Assertions.assertEquals(result.iterator().next().getName(), beverageName);
    }

    @Test
    public void insertShouldReturnBeverageDTO(){
        BeverageDTO result = service.insert(beverageDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), beverage.getId());
    }

    @Test
    public void updateShouldReturnBeverageDTOWhenIdExists(){
        BeverageDTO result = service.update(existingBeverageId, beverageDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingBeverageId);
        Assertions.assertEquals(result.getName(), beverage.getName());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingBeverageId, beverageDTO));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingBeverageId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingBeverageId));
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(DatabaseException.class, () -> service.delete(dependentBeverageId));
    }
}
