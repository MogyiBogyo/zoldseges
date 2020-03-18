package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.SaleDto;
import org.elte.zoldseges.dto.StockDto;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Sale;
import org.elte.zoldseges.entities.Stock;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    private Stock mapFromDtoToEntity(StockDto stockDto){
        Optional<Product> optionalProduct = productRepository.findById(stockDto.getProductId());
        if(optionalProduct.isPresent()){
            return new Stock(
                    optionalProduct.get(),
                    stockDto.getQuantity());
        } else {
            return new Stock(
                    null,
                    stockDto.getQuantity());
        }

    }

    private Stock modifyEntityWithDto(StockDto stockDto, Stock findedStock){
        Optional<Product> optionalProduct = productRepository.findById(stockDto.getProductId());
        if(optionalProduct.isPresent()) {
            findedStock.setProduct(optionalProduct.get());
            findedStock.setQuantity(stockDto.getQuantity());
        } else {
            findedStock.setQuantity(stockDto.getQuantity());
        }
        return findedStock;
    }


    /**
     * @return all stock
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Stock>> getAll() {
        return ResponseEntity.ok(stockRepository.findAll());
    }

    /**
     * @param id id from request
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


    @GetMapping("/{id}/product")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.get().getProduct());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * @param stockDto
     * @return add a new stock
     */
    @PostMapping("")
    public ResponseEntity<Stock> post(@RequestBody StockDto stockDto) {
        Stock savedStock = stockRepository.save(mapFromDtoToEntity(stockDto));
        return ResponseEntity.ok(savedStock);
    }


    /**
     * @param stockdto
     * @param id
     * @return modify a stock with this id, if it exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> put(@RequestBody StockDto stockdto, @PathVariable Integer id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            return ResponseEntity.ok(stockRepository.save(modifyEntityWithDto(stockdto, optionalStock.get())));
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

}
