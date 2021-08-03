package uz.pdp.money_transfer_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.money_transfer_demo.payload.CardDto;
import uz.pdp.money_transfer_demo.service.CardService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/card")
public class CardController {


    @Autowired
    CardService cardService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCard(){
        ResponseEntity<?> allCard = cardService.getAllCard();
        return allCard;
    }


    @GetMapping("/myInfo")
    public ResponseEntity<?> getMyCard(HttpServletRequest httpServletRequest){
        ResponseEntity<?> myCard = cardService.getMyCard(httpServletRequest);
        return myCard;
    }


    @PostMapping("/add")
    public HttpEntity<?> cardAdd(HttpServletRequest httpServletRequest, @RequestBody CardDto cardDto){
        return cardService.addCard(httpServletRequest, cardDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteCard(HttpServletRequest httpServletRequest,@PathVariable Integer id){
        return cardService.deleteCard(httpServletRequest,id);
    }

    @GetMapping("/income")
    public ResponseEntity<?> getMyCardIncomeList(HttpServletRequest httpServletRequest){
        ResponseEntity<?> myCardIncome = cardService.getIncomeMoneyList(httpServletRequest);
        return myCardIncome;
    }
    @GetMapping("/outcome")
    public ResponseEntity<?> getMyCardOutcomeList(HttpServletRequest httpServletRequest){
        ResponseEntity<?> myCardOutcome = cardService.getOutcomeMoneyList(httpServletRequest);
        return myCardOutcome;
    }
}
