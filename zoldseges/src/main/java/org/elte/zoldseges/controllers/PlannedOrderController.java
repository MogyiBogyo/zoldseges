package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.PlannedOrderDto;
import org.elte.zoldseges.entities.PlannedOrder;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.PlannedOrderRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Product> optionalProduct = productRepository.findById(plannedOrderDto.getProductId());
        if (optionalProduct.isPresent()) {
            return new PlannedOrder(
                    plannedOrderDto.getQuantity(),
                    optionalProduct.get()
            );
        } else {
            return new PlannedOrder(
                    plannedOrderDto.getQuantity(),
                    null
            );
        }
    }

    private PlannedOrder modifyEntityWithDto(PlannedOrderDto plannedOrderDto, PlannedOrder findedPOrder) {
        Optional<Product> optionalProduct = productRepository.findById(plannedOrderDto.getProductId());
        if (optionalProduct.isPresent()) {
            findedPOrder.setQuantity(plannedOrderDto.getQuantity());
            findedPOrder.setProduct(optionalProduct.get());
        } else {
            findedPOrder.setQuantity(plannedOrderDto.getQuantity());
        }
        return findedPOrder;
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
