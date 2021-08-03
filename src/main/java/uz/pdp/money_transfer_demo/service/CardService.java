package uz.pdp.money_transfer_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.money_transfer_demo.controller.CardController;
import uz.pdp.money_transfer_demo.entity.Card;
import uz.pdp.money_transfer_demo.entity.Income;
import uz.pdp.money_transfer_demo.entity.Outcome;
import uz.pdp.money_transfer_demo.payload.CardDto;
import uz.pdp.money_transfer_demo.repository.CardRepository;
import uz.pdp.money_transfer_demo.repository.IncomeRepository;
import uz.pdp.money_transfer_demo.repository.OutcomRepository;
import uz.pdp.money_transfer_demo.security.JwtFilter;
import uz.pdp.money_transfer_demo.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomRepository outcomRepository;

    public ResponseEntity<?> getMyCard(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        List<Card> byUsername = cardRepository.findByUsername(username);
        if (byUsername.isEmpty())
            return ResponseEntity.status(409).body("You have not card");
        return ResponseEntity.status(200).body(byUsername);


    }

    public HttpEntity<?> addCard(HttpServletRequest httpServletRequest, CardDto cardDto) {
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Card newCard=new Card();
        newCard.setBalance(cardDto.getBalance());
        newCard.setExpiredDate(cardDto.getExpiredDate());
        newCard.setNumber(cardDto.getNumber());
        newCard.setUsername(username);
        cardRepository.save(newCard);
        return ResponseEntity.ok("Card added");
    }

    public HttpEntity<?> deleteCard(HttpServletRequest httpServletRequest,Integer id) {
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent())
            return ResponseEntity.status(409).body("Card not found");
        if (optionalCard.get().getUsername().equals(username)) {
            cardRepository.deleteById(id);
            return ResponseEntity.status(200).body("Card deleted");
        }
        return ResponseEntity.status(409).body("You can not delete this card");

    }

    public ResponseEntity<?> getIncomeMoneyList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        List<Card> optionalCard = cardRepository.findByUsername(username);
        if (optionalCard.isEmpty())
            return ResponseEntity.status(409).body("You have not crd yet");
        List<Income> allByToAllCardId=new ArrayList<>();
        for (Card card : optionalCard) {
            if (incomeRepository.existsByToCardId(card.getId())) {
                List<Income> allByToCardId = incomeRepository.findAllByToCardId(card.getId());
                if (!allByToCardId.isEmpty()){
                    allByToAllCardId.addAll(allByToCardId);
                }
            }
        }
        return ResponseEntity.status(200).body(allByToAllCardId);
    }

    public ResponseEntity<?> getOutcomeMoneyList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        List<Card> optionalCard = cardRepository.findByUsername(username);
        if (optionalCard.isEmpty())
            return ResponseEntity.status(409).body("You have not crd yet");
        List<Outcome> allByFromAllCardId=new ArrayList<>();
        for (Card card : optionalCard) {
            if (outcomRepository.existsByFromCardId(card.getId())) {
                List<Outcome> allByFromCardId = outcomRepository.findAllByFromCardId(card.getId());
                if (allByFromCardId!=null){
                    allByFromAllCardId.addAll(allByFromCardId);
                }
            }
        }
        return ResponseEntity.status(200).body(allByFromAllCardId);
    }

    public ResponseEntity<?> getAllCard() {
        List<Card> cardList = cardRepository.findAll();
        return ResponseEntity.status(200).body(cardList);
    }
}
