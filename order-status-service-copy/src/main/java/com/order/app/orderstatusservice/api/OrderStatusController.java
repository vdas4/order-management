package com.order.app.orderstatusservice.api;
import com.order.app.orderstatusservice.entity.OrderStatusEntity;
import com.order.app.orderstatusservice.repo.OrderStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderStatusController {

    private final OrderStatusRepository repo;

    public OrderStatusController(OrderStatusRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> get(@PathVariable String orderId) {
        Optional<OrderStatusEntity> rec = repo.findById(orderId);
        return rec.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String status) {
        if (status == null) return ResponseEntity.ok(repo.findAll());
        return ResponseEntity.ok(
                repo.findAll().stream().filter(r -> status.equalsIgnoreCase(r.getStatus())).toList()
        );
    }
}

