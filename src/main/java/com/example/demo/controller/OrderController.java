package com.example.demo.controller;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import com.example.demo.Request.*;
import com.example.demo.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderRepository ordersRepo;
    private final OrderDetailRepository detailsRepo;

    public OrderController(OrderRepository ordersRepo, OrderDetailRepository detailsRepo) {
        this.ordersRepo = ordersRepo;
        this.detailsRepo = detailsRepo;
    }

    // === ORDER API ===

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(ordersRepo.findAll());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return ordersRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        if (ordersRepo.existsById(id)) {
            ordersRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request,
                                         HttpSession session) {
        Object idPerson = session.getAttribute("idPerson");
        if (idPerson == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }

        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.setIdperson((Integer) idPerson);
        order.setOrderdate(java.sql.Timestamp.valueOf(now));
        order.setStatus("Đang xử lý");
        order.setPaymentstatus("Chưa thanh toán");

        Order savedOrder = ordersRepo.save(order);

        for (OrderItem item : request.getItems()) {
            Orders_Detail detail = new Orders_Detail();
            detail.setIdorder(savedOrder.getIdorder());
            detail.setIdfood(item.getIdfood());
            detail.setQuantity(item.getQuantity());
            detailsRepo.save(detail);
        }

        return ResponseEntity.ok("Đặt món thành công!");
    }
}
