package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.StockDto;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.entities.Stock;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




/**
 * Allocates the "/stocks" endpoint to control stocks
 */
@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;


    /**
     * Creates a new Stock Entity from DTO
     * @param stockDto Stock transfer object
     * @return a new Stock
     */
    private Stock mapFromDtoToEntity(StockDto stockDto) {
            return new Stock(
                    productRepository.findById(stockDto.getProductId()).get(),
                    stockDto.getQuantity());
    }


    /**
     * Modify a Stock with DTO's data
     * @param stockDto data transfer object
     * @param foundedStock Stock for modify
     * @return an updated Stock
     */
    private Stock modifyEntityWithDto(StockDto stockDto, Stock foundedStock){
            foundedStock.setProduct(productRepository.findById(stockDto.getProductId()).get());
            foundedStock.setQuantity(stockDto.getQuantity());

        return foundedStock;
    }


    /**
     * Returns all the Stocks
     * @return ResponseEntity of Stocks
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Stock>> getAll() {
        return ResponseEntity.ok(stockRepository.findAll());
    }


    /**
     * Returns a Stock by ID
     * @param id Id of Stock
     * @return ResponseEntity of a Stock
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
     * Returns a Product of a Stock by Stock Id
     * @param id Id of a Stock
     * @return ResponseEntity of Product
     * Returns Not Found if Stock doesn't exists
     */
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
     * Creates a new Stock
     * @param stockDto The Stock data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created Stock
     */
    @PostMapping("")
    public ResponseEntity<Stock> post(@RequestBody StockDto stockDto) {
        Optional<Product> optionalProduct = productRepository.findById(stockDto.getProductId());
        if(optionalProduct.isPresent()) {
            Stock savedStock = stockRepository.save(mapFromDtoToEntity(stockDto));
            return ResponseEntity.ok(savedStock);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Updates a Stock by ID
     * @param id Id of Stock which to modify
     * @param stockdto The Stock data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Stock
     * Returns Not Found if Stock or Product doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> put(@RequestBody StockDto stockdto, @PathVariable Integer id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        Optional<Product> optionalProduct = productRepository.findById(stockdto.getProductId());
        if (optionalStock.isPresent() && optionalProduct.isPresent()) {
            return ResponseEntity.ok(stockRepository.save(modifyEntityWithDto(stockdto, optionalStock.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates a Stock with new quantity by Product Id
     * @param productId Id of Product which Stock wanted to modify
     * @param stockDto The Stock data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Stock
     * Returns Not Found if Stock doesn't exist.
     */
    @PutMapping("/product/{productId}")
    public ResponseEntity<Stock> putNewQuantity(@RequestBody StockDto stockDto, @PathVariable Integer productId){
        List<Stock> foundedStock = stockRepository.findByProductId(productId);
        if(!foundedStock.isEmpty()){
            Stock stock = foundedStock.get(0);
            Integer originalQuantity = stock.getQuantity();
            stock.setQuantity(originalQuantity + stockDto.getQuantity());
            return ResponseEntity.ok( stockRepository.save(stock));
        } else{
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Updates a Stock with decreased quantity by Product Id
     * @param productId Id of Product which Stock wanted to modify
     * @param stockDto The Stock data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Stock
     * Returns Not Found if Stock doesn't exist.
     * Returns Conflict if Stock quantity to small.
     */
    @PutMapping("/product/decrease/{productId}")
    public ResponseEntity<Stock> decreaseQuantity(@RequestBody StockDto stockDto, @PathVariable Integer productId){
        List<Stock> foundedStock = stockRepository.findByProductId(productId);
        if(!foundedStock.isEmpty()){
            Stock stock = foundedStock.get(0);
            int originalQuantity = stock.getQuantity();
            if(originalQuantity > stockDto.getQuantity()){
                stock.setQuantity(originalQuantity - stockDto.getQuantity());
            } else if(originalQuantity == stockDto.getQuantity()){
                stockRepository.deleteById(stock.getId());
                return ResponseEntity.ok().build();
            }
            else {
                return  ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.ok( stockRepository.save(stock));
        } else{
            return ResponseEntity.notFound().build();
        }
    }



    /**
     * Deletes a Stock by Stock ID
     * @param id ID of Stock
     * @return ResponseEntity
     * Returns Not Found if Stock doesn't exists
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
