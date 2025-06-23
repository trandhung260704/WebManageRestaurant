package com.example.demo.Request;

import com.example.demo.dto.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private int idPerson;
    private String status;
    private String paymentStatus;
    private List<OrderItem> items;
}
