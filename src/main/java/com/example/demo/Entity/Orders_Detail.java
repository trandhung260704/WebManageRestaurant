// Orders_Detail.java
package com.example.demo.Entity;

import com.example.demo.dto.OrdersDetailId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(OrdersDetailId.class)
@Table(name = "\"orders_detail\"")
public class Orders_Detail {

    @Id
    private int idorder;

    @Id
    private int idfood;

    private int quantity;
}
