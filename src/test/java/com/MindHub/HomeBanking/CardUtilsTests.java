package com.MindHub.HomeBanking;

import com.MindHub.HomeBanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;




@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class CardUtilsTests {

    @Test

    public void cardNumberIsCreated(){

        String cardNumber = CardUtils.getCardNumber(1000, 9999);

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }

    @Test

    public void getCardNumber(){

        String cardNumber = CardUtils.getCardNumber(1000, 9999);

        assertThat(cardNumber,is(cardNumber));

    }

    @Test

    public void CvvIsCreated(){

        int CVV = CardUtils.getCVV(100, 999);

        assertThat(CVV,notNullValue());

    }

    @Test

    public void getCvv(){

        int CVV = CardUtils.getCVV(100, 999);

        assertThat(CVV,is(CVV));

    }
}
