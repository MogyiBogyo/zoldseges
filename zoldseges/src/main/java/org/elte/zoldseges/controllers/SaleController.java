package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.ProductDto;
import org.elte.zoldseges.dto.SaleDto;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Sale;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;


    private Sale mapFromDtoToEntity(SaleDto saleDto){
        Optional<Product> product =  productRepository.findById(saleDto.getProductId());
        if (product.isPresent()){
            return new Sale(
                    saleDto.getQuantity(),
                    saleDto.getDate(),
                    saleDto.getBuyer(),
                    saleDto.getPrice(),
                    product.get());
        } else {
            return new Sale(
                    saleDto.getQuantity(),
                    saleDto.getDate(),
                    saleDto.getBuyer(),
                    saleDto.getPrice(),
                    null);
        }
    }

    private Sale modifyEntityWithDto(SaleDto saleDto, Sale findedSale){
       Optional<Product> product =  productRepository.findById(saleDto.getProductId());
        if (product.isPresent()) {
            findedSale.setBuyer(saleDto.getBuyer());
            findedSale.setDate(saleDto.getDate());
            findedSale.setPrice(saleDto.getPrice());
            findedSale.setQuantity(saleDto.getQuantity());
            findedSale.setProduct(product.get());
        } else {
            findedSale.setBuyer(saleDto.getBuyer());
            findedSale.setDate(saleDto.getDate());
            findedSale.setPrice(saleDto.getPrice());
            findedSale.setQuantity(saleDto.getQuantity());
            findedSale.setProduct(null);
        }
        return findedSale;
    }

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
     * @param saledto
     * @return add a new sale
     */
    @PostMapping("")
    public ResponseEntity<Sale> post(@RequestBody SaleDto saledto) {
        Sale savedSale = saleRepository.save(mapFromDtoToEntity(saledto));
        return ResponseEntity.ok(savedSale);
    }

    /**
     * @param saledto
     * @param id
     * @return  modify a sale if it exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sale> put(@RequestBody SaleDto saledto, @PathVariable Integer id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            //sale.setId(id);
            return ResponseEntity.ok(saleRepository.save(modifyEntityWithDto(saledto, optionalSale.get())));
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





}
