package uz.pdp.money_transfer_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String username;

    @Column(nullable = false,unique = true)
    private  String number;

    private double balance;

    private Date expiredDate;

    private boolean active=true;

}
