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


@CrossOrigin
@RestController
@RequestMapping("/plans")
public class PlannedOrderController {
    @Autowired
    private PlannedOrderRepository plannedOrderRepository;

    @Autowired
    private ProductRepository productRepository;


    private PlannedOrder mapFromDtoToEntity(PlannedOrderDto plannedOrderDto) {
            return new PlannedOrder(
                    plannedOrderDto.getQuantity(),
                    productRepository.findById(plannedOrderDto.getProductId()).get()
            );
    }

    private PlannedOrder modifyEntityWithDto(PlannedOrderDto plannedOrderDto, PlannedOrder findedOrder) {
            findedOrder.setQuantity(plannedOrderDto.getQuantity());
            findedOrder.setProduct(productRepository.findById(plannedOrderDto.getProductId()).get());
        return findedOrder;
    }


    /**
     * @return all planned order
     */
    @GetMapping("")
    public ResponseEntity<Iterable<PlannedOrder>> getAll() {
        return ResponseEntity.ok(plannedOrderRepository.findAll());
    }

    /**
     * @param id
     * @return plan with this id
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
     * @param plannedOrderDto
     * @return add a new plan
     */
    @PostMapping("")
    public ResponseEntity<PlannedOrder> post(@RequestBody PlannedOrderDto plannedOrderDto) {
        PlannedOrder savedPlan = plannedOrderRepository.save(mapFromDtoToEntity(plannedOrderDto));
        return ResponseEntity.ok(savedPlan);
    }

    /**
     * @param plannedOrderDto
     * @param id
     * @return modify a plan if the id exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlannedOrder> put(@RequestBody PlannedOrderDto plannedOrderDto, @PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        if (optionalPlannedOrder.isPresent()) {
            return ResponseEntity.ok(plannedOrderRepository.save(modifyEntityWithDto(plannedOrderDto, optionalPlannedOrder.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * @param plannedOrderDto a simple dto
     * @param productId id of a product
     * @return  find a plannes order by product id, and modify it's quantity
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
     * @param id
     * @return delet a plan if it exists
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
