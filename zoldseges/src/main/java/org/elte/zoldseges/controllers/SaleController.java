package org.elte.zoldseges.controllers;

import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Sale;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * @return all sale
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Sale>> getAll() {
        return ResponseEntity.ok(saleRepository.findAll());
    }

    /**
     * @param id
     * @return sale with this id
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
     * @param sale
     * @return add a new sale
     */
    @PostMapping("")
    public ResponseEntity<Sale> post(@RequestBody Sale sale) {
        Sale savedSale = saleRepository.save(sale);
        return ResponseEntity.ok(savedSale);
    }



    /**
     * @param sale
     * @param id
     * @return  modify a sale if it exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sale> put(@RequestBody Sale sale, @PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            //sale.setId(id);
            return ResponseEntity.ok(saleRepository.save(sale));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id
     * @return deletes a sale with this id, if it exists
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

    /*//TODO: get products
    @GetMapping("/{id}/product")
    public ResponseEntity<Iterable<Product>> getProducts(@PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            return ResponseEntity.ok(optionalSale.get().getProductList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */

}
