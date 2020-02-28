package org.elte.zoldseges.controllers;

import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Stock;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;


    /**
     * @return all stock
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Stock>> getAll() {
        return ResponseEntity.ok(stockRepository.findAll());
    }

    /**
     * @param id
     * @return stock data with this id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> get(@PathVariable Integer id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * @param stock
     * @return add a new stock
     */
    @PostMapping("")
    public ResponseEntity<Stock> post(@RequestBody Stock stock) {
        Stock savedStock = stockRepository.save(stock);
        return ResponseEntity.ok(savedStock);
    }


    /**
     * @param stock
     * @param id
     * @return modify a stock with this id, if it exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> put(@RequestBody Stock stock, @PathVariable Integer id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            //stock.setId(id);
            return ResponseEntity.ok(stockRepository.save(stock));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id
     * @return delet a stock with this id, if it exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            stockRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@GetMapping("/{id}/products")
    public ResponseEntity<Iterable<Product>> getProducts(@PathVariable Integer id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            return ResponseEntity.ok(optionalStock.get().getProductList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/


}
