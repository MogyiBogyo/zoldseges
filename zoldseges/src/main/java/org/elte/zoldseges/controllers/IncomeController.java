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

@CrossOrigin
@RestController
@RequestMapping("/incomes")
public class IncomeController {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ProductRepository productRepository;


    private Income mapFromDtoToEntity(IncomeDto incomeDto){
        Optional<Product> optionalProduct = productRepository.findById( incomeDto.getId());
        return new Income(
                incomeDto.getQuantity(),
                incomeDto.getSeller(),
                incomeDto.getDate(),
                incomeDto.getPrice(),
                optionalProduct.get()
        );
    }

    private Income modifyEntityWithDto(IncomeDto incomeDto, Income findedIncome){
        Optional<Product> optionalProduct = productRepository.findById( incomeDto.getId());
            findedIncome.setQuantity(incomeDto.getQuantity());
            findedIncome.setDate(incomeDto.getDate());
            findedIncome.setPrice(incomeDto.getPrice());
            findedIncome.setSeller(incomeDto.getSeller());
            findedIncome.setProduct(optionalProduct.get());
        return findedIncome;
    }


    /**
     * @return all Income
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Income>> getAll() {
        return ResponseEntity.ok(incomeRepository.findAll());
    }


    /**
     * @param id
     * @return income date with this id
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
     * @param incomeDto
     * @return add a new income
     */
    @PostMapping("")
    public ResponseEntity<Income> post(@RequestBody IncomeDto incomeDto) {
        Income savedIncome = incomeRepository.save(mapFromDtoToEntity(incomeDto));
        return ResponseEntity.ok(savedIncome);
    }

    /**
     * @param incomeDto
     * @param id
     * @return modify an income with this id, if it exists
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
     * @param id
     * @return deletes an income with this id, if it exists
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
