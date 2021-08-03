package uz.pdp.money_transfer_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.money_transfer_demo.entity.Card;
import uz.pdp.money_transfer_demo.entity.Income;
import uz.pdp.money_transfer_demo.entity.Outcome;
import uz.pdp.money_transfer_demo.payload.OutComeDto;
import uz.pdp.money_transfer_demo.repository.CardRepository;
import uz.pdp.money_transfer_demo.repository.IncomeRepository;
import uz.pdp.money_transfer_demo.repository.OutcomRepository;
import uz.pdp.money_transfer_demo.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class TransferService {
    double commissionFree=0.02;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomRepository outcomRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CardRepository cardRepository;


    public HttpEntity<?> transferMoney(HttpServletRequest httpServletRequest, OutComeDto outComeDto) {
        String token = httpServletRequest.getHeader("Authorization");
        token=token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Card> optionalCard = cardRepository.findById(outComeDto.getFromCardId());
        if (!optionalCard.isPresent())
            return ResponseEntity.status(409).body("Your card not found");
        boolean equals = optionalCard.get().getUsername().equals(username);
        if (!equals)
            return ResponseEntity.status(409).body("This card is not yours");
        Optional<Card> optionalCard1 = cardRepository.findById(outComeDto.getToCardId());
        if (!optionalCard1.isPresent())
            return ResponseEntity.status(409).body("The receving card is not available");
        if (outComeDto.getAmount()>optionalCard.get().getBalance()) {
            return ResponseEntity.status(409).body("not enougth money");
        }

        Card senderCard = optionalCard.get();
        senderCard.setBalance(senderCard.getBalance()-(outComeDto.getAmount()+outComeDto.getAmount()*commissionFree));


        Outcome outcome=new Outcome();
        outcome.setAmount(outComeDto.getAmount());
        outcome.setDate(new Date());
        outcome.setCommissionAmount(outComeDto.getAmount()*commissionFree);
        outcome.setFromCard(optionalCard.get());
        outcome.setToCard(optionalCard1.get());
        outcomRepository.save(outcome);

        Income income=new Income();
        income.setAmount(outComeDto.getAmount());
        income.setFromCard(optionalCard.get());
        income.setToCard(optionalCard1.get());
        income.setDate(new Date());
        incomeRepository.save(income);

        return ResponseEntity.status(200).body("Successfully transfered");

    }
}
