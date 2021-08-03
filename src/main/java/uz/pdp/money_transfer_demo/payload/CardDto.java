package uz.pdp.money_transfer_demo.payload;

import lombok.Data;

import java.util.Date;

@Data
public class CardDto {
    private  String number;
    private double balance;
    private Date expiredDate;
}
