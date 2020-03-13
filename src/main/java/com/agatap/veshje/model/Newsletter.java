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
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToMany(mappedBy = "newsletter")
    @Builder.Default
    private List<User> users = new ArrayList<>();
    //todo think about
}
