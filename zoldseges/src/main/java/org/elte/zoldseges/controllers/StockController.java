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

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    private Stock mapFromDtoToEntity(StockDto stockDto) {
            return new Stock(
                    productRepository.findById(stockDto.getProductId()).get(),
                    stockDto.getQuantity());
    }

    private Stock modifyEntityWithDto(StockDto stockDto, Stock foundedStock){
            foundedStock.setProduct(productRepository.findById(stockDto.getProductId()).get());
            foundedStock.setQuantity(stockDto.getQuantity());

        return foundedStock;
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
        Optional<Product> optionalProduct = productRepository.findById(stockDto.getProductId());
        if(optionalProduct.isPresent()) {
            Stock savedStock = stockRepository.save(mapFromDtoToEntity(stockDto));
            return ResponseEntity.ok(savedStock);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * @param stockdto
     * @param id
     * @return modify a stock with this id, if it exists
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

    //olyan endponint ami productid alapján modositja a stockot
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


    @PutMapping("/product/decrease/{productId}")
    public ResponseEntity<Stock> decreaseQuantity(@RequestBody StockDto stockDto, @PathVariable Integer productId){
        List<Stock> foundedStock = stockRepository.findByProductId(productId);
        if(!foundedStock.isEmpty()){
            Stock stock = foundedStock.get(0);
            int originalQuantity = stock.getQuantity();
            if(originalQuantity >= stockDto.getQuantity()){
                stock.setQuantity(originalQuantity - stockDto.getQuantity());
            } else {
                return  ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.ok( stockRepository.save(stock));
        } else{
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
