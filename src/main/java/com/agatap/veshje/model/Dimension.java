package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer bust;
    private Integer waist;
    private Integer hips;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToOne(mappedBy = "dimension")
    private Size size;
}
