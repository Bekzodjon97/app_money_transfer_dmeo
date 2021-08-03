package uz.pdp.money_transfer_demo.payload;

import lombok.Data;
import uz.pdp.money_transfer_demo.entity.Card;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class OutComeDto {
    private Integer fromCardId;

    private Integer toCardId;

    private double amount;
}
