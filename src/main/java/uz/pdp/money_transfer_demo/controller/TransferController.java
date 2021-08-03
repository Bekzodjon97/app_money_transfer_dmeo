package uz.pdp.money_transfer_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.money_transfer_demo.payload.OutComeDto;
import uz.pdp.money_transfer_demo.service.TransferService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping("/send")
    public HttpEntity<?> transferMoney(HttpServletRequest httpServletRequest, @RequestBody OutComeDto outComeDto){
        return transferService.transferMoney(httpServletRequest,outComeDto);

    }

}
