package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;
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
//    private Delivery deliveryId;
//    private User userId;
//    private Payment paymentId;
//    private Integer List<Product> products;
}
