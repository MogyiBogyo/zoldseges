package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.SaleDto;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Sale;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Allocates the "/sales" endpoint to control sales
 */
@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new Sale Entity from DTO
     * @param saleDto data transfer object
     * @return a new Sale
     */
    private Sale mapFromDtoToEntity(SaleDto saleDto) {
        return new Sale(
                saleDto.getQuantity(),
                saleDto.getDate(),
                saleDto.getBuyer(),
                saleDto.getPrice(),
                productRepository.findById(saleDto.getProductId()).get());

    }

    /**
     * Modify a Sale with DTO's data
     * @param saleDto data transfer object
     * @param foundedSale Sale for modify
     * @return an updated Sale
     */
    private Sale modifyEntityWithDto(SaleDto saleDto, Sale foundedSale) {
        foundedSale.setBuyer(saleDto.getBuyer());
        foundedSale.setDate(saleDto.getDate());
        foundedSale.setPrice(saleDto.getPrice());
        foundedSale.setQuantity(saleDto.getQuantity());
        foundedSale.setProduct(productRepository.findById(saleDto.getProductId()).get());

        return foundedSale;
    }

    /**
     * Returns all the Sales
     * @return ResponseEntity of Sales
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Sale>> getAll() {
        return ResponseEntity.ok(saleRepository.findAll());
    }

    /**
     * Returns a Sale by ID
     * @param id Id of Sale
     * @return ResponseEntity of a Sale
     * Returns Not Found if Sale doesn't exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sale> get(@PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            return ResponseEntity.ok(optionalSale.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns a Product of a Sale by ID
     * @param id Id of Sale
     * @return ResponseEntity of a Sale
     * Returns Not Found if Sale doesn't exists
     */
    @GetMapping("/{id}/product")
    public ResponseEntity<Product> getProducts(@PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            return ResponseEntity.ok(optionalSale.get().getProduct());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new Sale
     * @param saledto The Sale data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created Sale
     */
    @PostMapping("")
    public ResponseEntity<Sale> post(@RequestBody SaleDto saledto) {
        Optional<Product> optionalProduct = productRepository.findById(saledto.getProductId());
        if(optionalProduct.isPresent()){
            Sale savedSale = saleRepository.save(mapFromDtoToEntity(saledto));
            return ResponseEntity.ok(savedSale);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Updates a Sale by ID
     * @param id Id of Sale which to modify
     * @param saledto The Sale data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Sale
     * Returns Not Found if Sale or Product doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sale> put(@RequestBody SaleDto saledto, @PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Optional<Product> optionalProduct = productRepository.findById(saledto.getProductId());
        if (optionalSale.isPresent() && optionalProduct.isPresent()) {
            return ResponseEntity.ok(saleRepository.save(modifyEntityWithDto(saledto, optionalSale.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a Sale by Sale ID
     * @param id ID of Sale
     * @return ResponseEntity
     * Returns Not Found if Sale doesn't exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            saleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
