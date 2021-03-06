package id.co.bri.api.fake;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BriFakeRestController {

  public static boolean slow = false;

  public static class CreateChargeRequest {
    public int amount;
  }

  public static class CreateChargeResponse {
    public String transactionId;
    public String errorCode;
  }

  private static final double SIXTY_SECONDS = 60 * 1000;

  @RequestMapping(path = "/charge", method = POST)
  public CreateChargeResponse chargeDebitCard(@RequestBody CreateChargeRequest request) throws Exception {
    CreateChargeResponse response = new CreateChargeResponse();

    long waitTimeMillis = 0;
    if (slow) {
      waitTimeMillis = Math.round(Math.random() * SIXTY_SECONDS);
    }

    if (Math.random() > 0.8d) {
      response.errorCode = "Debit card expired";
    }

    System.out.println("Charge on debit card will take " + waitTimeMillis / 1000 + " seconds");
    Thread.sleep(waitTimeMillis);

    response.transactionId = UUID.randomUUID().toString();
    return response;
  }

}