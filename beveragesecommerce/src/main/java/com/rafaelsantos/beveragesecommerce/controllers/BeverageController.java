package com.rafaelsantos.beveragesecommerce.controllers;

import com.rafaelsantos.beveragesecommerce.entities.BeverageMinDTO;
import com.rafaelsantos.beveragesecommerce.entities.DTO.BeverageDTO;
import com.rafaelsantos.beveragesecommerce.services.BeverageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/beverages")
public class BeverageController {

    private final BeverageService beverageService;

    public BeverageController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping
    public ResponseEntity<Page<BeverageMinDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name,
                                                        Pageable pageable){

        Page<BeverageMinDTO> dto = beverageService.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeverageDTO> findById(@PathVariable Long id){
        BeverageDTO dto = beverageService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BeverageDTO> insert(@Valid @RequestBody BeverageDTO dto){
        dto = beverageService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<BeverageDTO> update(@PathVariable Long id, @Valid @RequestBody BeverageDTO dto){
        dto = beverageService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        beverageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
