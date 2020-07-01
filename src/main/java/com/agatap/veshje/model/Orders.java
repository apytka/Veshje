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
    private Double totalAmount;

    @OneToOne(mappedBy = "orders")
    private Payments payment;
    private String paymentType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Delivery delivery;
    private String deliveryType;
    private Double deliveryPrice;
    @ManyToOne
    private User user;
    @ManyToMany
    @Builder.Default
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderItem> orderItem = new ArrayList<>();
    @OneToOne(mappedBy = "order")
    private OrderAddressData orderAddressData;
    @ManyToOne
    private CouponCode couponsCode;
}
