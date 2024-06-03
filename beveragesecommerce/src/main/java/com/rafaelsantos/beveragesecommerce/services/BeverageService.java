package com.rafaelsantos.beveragesecommerce.services;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.BeverageMinDTO;
import com.rafaelsantos.beveragesecommerce.entities.Category;
import com.rafaelsantos.beveragesecommerce.entities.DTO.BeverageDTO;
import com.rafaelsantos.beveragesecommerce.entities.DTO.CategoryDTO;
import com.rafaelsantos.beveragesecommerce.repositories.BeverageRepository;
import com.rafaelsantos.beveragesecommerce.services.exceptions.DatabaseException;
import com.rafaelsantos.beveragesecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeverageService {

    @Autowired
    private BeverageRepository beverageRepository;

    @Transactional(readOnly = true)
    public BeverageDTO findById(Long id) {
        Beverage beverage = beverageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new BeverageDTO(beverage);
    }

    @Transactional(readOnly = true)
    public Page<BeverageMinDTO> findAll(String name, Pageable pageable){
        Page<Beverage> list = beverageRepository.searchByName(name, pageable);
        return list.map(BeverageMinDTO::new);
    }

    @Transactional
    public BeverageDTO insert(BeverageDTO dto){
        Beverage entity = new Beverage();
        copyDtoToEntity(dto, entity);
        entity = beverageRepository.save(entity);

        return new BeverageDTO(entity);
    }

    @Transactional
    public BeverageDTO update(Long id,BeverageDTO dto){
        try{
            Beverage entity = beverageRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = beverageRepository.save(entity);
            return new BeverageDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    public void delete(Long id){
        if(!beverageRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            beverageRepository.deleteById(id);
        }

        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Fala de integridade referencial");
        }
    }

    private void copyDtoToEntity(BeverageDTO dto, Beverage entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()) {
            Category cat = new Category();
            cat.setId(catDto.getId());
            entity.getCategories().add(cat);
        }
    }
}
