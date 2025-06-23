// OrdersDetailId.java
package com.example.demo.dto;

import java.io.Serializable;
import java.util.Objects;

public class OrdersDetailId implements Serializable {
    private int idorder;
    private int idfood;

    public OrdersDetailId() {}

    public OrdersDetailId(int idorder, int idfood) {
        this.idorder = idorder;
        this.idfood = idfood;
    }

    // equals & hashCode bắt buộc để làm composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdersDetailId)) return false;
        OrdersDetailId that = (OrdersDetailId) o;
        return idorder == that.idorder && idfood == that.idfood;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idorder, idfood);
    }
}
