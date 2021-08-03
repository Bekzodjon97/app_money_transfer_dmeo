package uz.pdp.money_transfer_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public ResponseEntity<?> getHomePage(){
        return ResponseEntity.ok("Authorizatsiyadan o'ting, buning uchun /api/auth/login yo'liga murojat qiling");
    }
}
