package org.elte.zoldseges.controllers;

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

    /**
     * @param plannedOrder
     * @return add a new plan
     */
    @PostMapping("")
    public ResponseEntity<PlannedOrder> post(@RequestBody PlannedOrder plannedOrder) {
        PlannedOrder savedPlan = plannedOrderRepository.save(plannedOrder);
        return ResponseEntity.ok(savedPlan);
    }

    /**
     * @param plannedOrder
     * @param id
     * @return modify a plan if the id exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlannedOrder> put(@RequestBody PlannedOrder plannedOrder, @PathVariable Integer id) {
        Optional<PlannedOrder> optionalPlannedOrder = plannedOrderRepository.findById(id);
        if (optionalPlannedOrder.isPresent()) {
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
