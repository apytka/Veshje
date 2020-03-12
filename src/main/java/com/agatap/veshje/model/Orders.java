package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private OrderStatus orderStatus;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToOne(mappedBy = "orders")
    private Payments payment;
    @ManyToOne(fetch = FetchType.EAGER)
    private Delivery delivery;
    @ManyToOne
    private User userOrders;
    @ManyToMany
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
