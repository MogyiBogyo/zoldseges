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

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;


    private Sale mapFromDtoToEntity(SaleDto saleDto) {
        return new Sale(
                saleDto.getQuantity(),
                saleDto.getDate(),
                saleDto.getBuyer(),
                saleDto.getPrice(),
                productRepository.findById(saleDto.getProductId()).get());

    }

    private Sale modifyEntityWithDto(SaleDto saleDto, Sale foundedSale) {
        foundedSale.setBuyer(saleDto.getBuyer());
        foundedSale.setDate(saleDto.getDate());
        foundedSale.setPrice(saleDto.getPrice());
        foundedSale.setQuantity(saleDto.getQuantity());
        foundedSale.setProduct(productRepository.findById(saleDto.getProductId()).get());

        return foundedSale;
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
        Optional<Product> optionalProduct = productRepository.findById(saledto.getProductId());
        if(optionalProduct.isPresent()){
            Sale savedSale = saleRepository.save(mapFromDtoToEntity(saledto));
            return ResponseEntity.ok(savedSale);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * @param saledto
     * @param id
     * @return modify a sale if it exists
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
