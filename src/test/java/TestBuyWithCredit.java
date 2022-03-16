import com.codeborne.selenide.logevents.SelenideLogger;
import date.DBHelper;
import date.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.*;
import org.junit.jupiter.api.*;

import page.MainPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestBuyWithCredit {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeEach
    void shouldOpenApp() {
        DBHelper.clean();
        open("http://localhost:8080", MainPage.class);
        mainPage.buyWithCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @Test
    void shouldApproveCardForCredit() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectApprovalFromBank();
        val expected = DataHelper.getValidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }


    @Test
    void shouldDeclinedValidCardForCredit() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFormIsEmpty() {
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldRandomCard() {
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBuyWithoutCardNumber() {
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditWithoutMonth() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditWithoutMonthInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditWithInvalidMonth() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getInValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldCreditWithInvalidMonthInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getInValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldCreditWithoutYearInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditWithoutYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditInvalidYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldCreditInvalidYearInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }


    @Test
    void shouldCreditWithoutCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldCreditWithoutCardHolderInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldCreditRandomCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getRandomCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldCreditRandomCardHolderInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getRandomCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldCreditWithoutCVCInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditWithoutCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }


    @Test
    void shouldCreditRandomCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getInvalidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldCreditRandomCVCInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getInvalidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }
}
