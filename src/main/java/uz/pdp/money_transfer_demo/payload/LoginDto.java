package uz.pdp.money_transfer_demo.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
