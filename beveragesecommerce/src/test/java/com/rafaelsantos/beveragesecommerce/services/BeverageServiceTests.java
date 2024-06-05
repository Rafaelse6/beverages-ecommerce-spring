package com.rafaelsantos.beveragesecommerce.services;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.BeverageMinDTO;
import com.rafaelsantos.beveragesecommerce.entities.Category;
import com.rafaelsantos.beveragesecommerce.entities.DTO.BeverageDTO;
import com.rafaelsantos.beveragesecommerce.factories.BeverageFactory;
import com.rafaelsantos.beveragesecommerce.repositories.BeverageRepository;
import com.rafaelsantos.beveragesecommerce.repositories.CategoryRepository;
import com.rafaelsantos.beveragesecommerce.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
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

    private long existingBeverageId, nonExistingBeverageId;
    private String beverageName;
    private Beverage beverage;
    private BeverageDTO beverageDTO;

    private Category category;

    private PageImpl<Beverage> page;

    @BeforeEach
    void setUp() throws Exception{
        existingBeverageId  = 1L;
        nonExistingBeverageId = 2L;

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
}
