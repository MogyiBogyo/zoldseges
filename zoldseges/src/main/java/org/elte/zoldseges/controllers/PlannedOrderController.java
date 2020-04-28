package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.PlannedOrderDto;
import org.elte.zoldseges.entities.PlannedOrder;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.PlannedOrderRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Allocates the "/plans" endpoint to control planned orders
 */
@CrossOrigin
@RestController
@RequestMapping("/plans")
public class PlannedOrderController {
    @Autowired
    private PlannedOrderRepository plannedOrderRepository;

    @Autowired
    private ProductRepository productRepository;


    /**
     * Creates a new PlannedOrder Entity from DTO
     * @param plannedOrderDto data transfer object
     * @return a new PlannedOrder
     */
    private PlannedOrder mapFromDtoToEntity(PlannedOrderDto plannedOrderDto) {
            return new PlannedOrder(
                    plannedOrderDto.getQuantity(),
                    productRepository.findById(plannedOrderDto.getProductId()).get()
            );
    }

    /**
     * Modify a PlannedOrder with DTO's data
     * @param plannedOrderDto data transfer object
     * @param findedOrder PlannedOrder for modify
     * @return an updated PlannedOrder
     */
    private PlannedOrder modifyEntityWithDto(PlannedOrderDto plannedOrderDto, PlannedOrder findedOrder) {
            findedOrder.setQuantity(plannedOrderDto.getQuantity());
            findedOrder.setProduct(productRepository.findById(plannedOrderDto.getProductId()).get());
        return findedOrder;
    }


    /**
     * Returns all the PlannedOrder
     * @return ResponseEntity of PlannedOrders
     */
    @GetMapping("")
    public ResponseEntity<Iterable<PlannedOrder>> getAll() {
        return ResponseEntity.ok(plannedOrderRepository.findAll());
    }


    /**
     * Returns a PlannedOrder by ID
     * @param id Id of PlannedOrder
     * @return ResponseEntity of a PlannedOrder
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlannedOrder> get(@PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        if (optionalPlannedOrder.isPresent()) {
            return ResponseEntity.ok(optionalPlannedOrder.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns a Product of a PlannedOrder
     * @param id Id of PlannedOrder
     * @return ResponseEntity of Product
     */
    @GetMapping("/{id}/product")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        if (optionalPlannedOrder.isPresent()) {
            return ResponseEntity.ok(optionalPlannedOrder.get().getProduct());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new PlannedOrder
     * @param plannedOrderDto The PlannedOrder Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created PlannedOrder
     * Returns Not Found if Product from DTO doesn't exists
     */
    @PostMapping("")
    public ResponseEntity<PlannedOrder> post(@RequestBody PlannedOrderDto plannedOrderDto) {
        Optional<Product> optionalProduct = productRepository.findById(plannedOrderDto.getProductId());
        if(optionalProduct.isPresent()){
            PlannedOrder savedPlan = plannedOrderRepository.save(mapFromDtoToEntity(plannedOrderDto));
            return ResponseEntity.ok(savedPlan);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Updates a PlannedOrder by ID
     * @param id Id of PlannedOrder which to modify
     * @param plannedOrderDto The Group Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated PlannedOrder
     * Returns Not Found if PlannedOrder or Product doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlannedOrder> put(@RequestBody PlannedOrderDto plannedOrderDto, @PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        Optional<Product> optionalProduct = productRepository.findById(plannedOrderDto.getProductId());
        if (optionalPlannedOrder.isPresent() && optionalProduct.isPresent()) {
            return ResponseEntity.ok(plannedOrderRepository.save(modifyEntityWithDto(plannedOrderDto, optionalPlannedOrder.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Updates a PlannedOrder or make new one
     * @param productId Id of Product which to modify
     * @param plannedOrderDto The PlannedOrderDto Object to add or modify PlannedOrder to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Group
     * Returns Not Found if Group doesn't exist.
     */
    @PutMapping("/product/{productId}")
    public ResponseEntity<PlannedOrder> putPlannedOrderOrModifyOne (@RequestBody PlannedOrderDto plannedOrderDto,@PathVariable Integer productId){
        List<PlannedOrder> foundedPlannedOrder = plannedOrderRepository.findByProductId(productId);
        if(!foundedPlannedOrder.isEmpty()){
            PlannedOrder plannedOrder = foundedPlannedOrder.get(0);
            Integer originalQuantity = plannedOrder.getQuantity();
            plannedOrder.setQuantity(originalQuantity + plannedOrderDto.getQuantity());
            return ResponseEntity.ok(plannedOrderRepository.save(plannedOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Deletes a PlannedOrder by PlannedOrder ID
     * @param id ID of PlannedOrder
     * @return ResponseEntity
     * Returns Not Found if Group doesn't exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        if (optionalPlannedOrder.isPresent()) {
            plannedOrderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
