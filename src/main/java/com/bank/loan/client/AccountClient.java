package com.bank.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@FeignClient(value="accounts", url="http://localhost:7080")
public interface AccountClient {

    @RequestMapping(value ="/accounts/update-money/{accountId}", method= RequestMethod.PUT)
    void updateAccount (@RequestParam int accountId, @RequestParam BigDecimal money);

}
