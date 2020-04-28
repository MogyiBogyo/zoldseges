package org.elte.zoldseges.controllers;


import org.elte.zoldseges.dto.IncomeDto;
import org.elte.zoldseges.entities.Income;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.IncomeRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Allocates the "/incomes" endpoint to control incomes
 */
@CrossOrigin
@RestController
@RequestMapping("/incomes")
public class IncomeController {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new Income Entity from DTO
     * @param incomeDto data transfer object
     * @return a new Income
     */
    private Income mapFromDtoToEntity(IncomeDto incomeDto) {
            return new Income(
                    incomeDto.getQuantity(),
                    incomeDto.getSeller(),
                    incomeDto.getDate(),
                    incomeDto.getPrice(),
                   productRepository.findById(incomeDto.getProductId()).get()
            );

    }

    /**
     * Modify a Income with DTO's data
     * @param incomeDto data transfer object
     * @param founded Income for modify
     * @return an updated Income
     */
    private Income modifyEntityWithDto(IncomeDto incomeDto, Income founded) {
        founded.setQuantity(incomeDto.getQuantity());
        founded.setDate(incomeDto.getDate());
        founded.setPrice(incomeDto.getPrice());
        founded.setSeller(incomeDto.getSeller());
        founded.setProduct(productRepository.findById(incomeDto.getProductId()).get());
        return founded;
    }


    /**
     * Returns all the Income
     * @return ResponseEntity of Income
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Income>> getAll() {
        return ResponseEntity.ok(incomeRepository.findAll());
    }


    /**
     * Returns a Income by ID
     * @param id Id of an Income
     * @return ResponseEntity of a Income
     */
    @GetMapping("/{id}")
    public ResponseEntity<Income> get(@PathVariable Integer id) {
        Optional<Income> income = incomeRepository.findById(id);
        if (income.isPresent()) {
            return ResponseEntity.ok(income.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new Income
     * @param incomeDto The IncomeDto Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created Income
     */
    @PostMapping("")
    public ResponseEntity<Income> post(@RequestBody IncomeDto incomeDto) {
        Optional<Product> optionalProduct = productRepository.findById(incomeDto.getProductId());
        if(optionalProduct.isPresent()){
            Income savedIncome = incomeRepository.save(mapFromDtoToEntity(incomeDto));
            return ResponseEntity.ok(savedIncome);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Updates a Income by ID
     * @param id Id of Income which to modify
     * @param incomeDto The Income Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Income
     * Returns Not Found if Income doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Income> put(@RequestBody IncomeDto incomeDto, @PathVariable Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            return ResponseEntity.ok(incomeRepository.save(modifyEntityWithDto(incomeDto, optionalIncome.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a Income by Income ID
     * @param id ID of Income
     * @return ResponseEntity
     * Returns Not Found if Income doesn't exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            incomeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
