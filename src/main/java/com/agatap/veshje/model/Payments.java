package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal amount;
    private PaymentsStatus paymentStatus;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    @ManyToOne
    private PaymentsType typePayment;
    @OneToOne(fetch = FetchType.LAZY)
    private Orders orders;
    @ManyToOne
    private User users;
}
