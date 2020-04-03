package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class VerificationToken {
//    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;
//    @Transient
//    private Date expiryDate;

    @OneToOne
    private User user;

//    private Date calculateExpiryDate(int expiryTimeInMinutes) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Timestamp(cal.getTime().getTime()));
//        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
//        return new Date(cal.getTime().getTime());
//    }
}
