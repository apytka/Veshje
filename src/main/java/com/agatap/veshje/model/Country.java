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
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    @Builder.Default
    private List<City> cities = new ArrayList<>();
}
