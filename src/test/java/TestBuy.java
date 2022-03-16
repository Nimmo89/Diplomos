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

public class TestBuy {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeEach
    void shouldOpenApp() {
//        DBHelper.clean();
        open("http://localhost:8080", MainPage.class);
        mainPage.buyCard();
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
    void shouldApproveCardBuy() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectApprovalFromBank();
        val expected = DataHelper.getValidCardStatus();
        val actual = DBHelper.getStatusBuy();
        assertEquals(expected, actual);
    }


    @Test
    void shouldDeclinedValidCardBuy() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFormIsEmptyBuy() {
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
    void shouldRandomCardBuy() {
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusBuy();
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
    void shouldBuyWithoutMonthInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldBuyWithInvalidMonth() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getInValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldBuyWithInvalidMonthInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getInValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldBuyWithoutYearInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldBuyWithoutYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldBuyInvalidYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldBuyInvalidYearInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }


    @Test
    void shouldBuyWithoutCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldBuyWithoutCardHolderInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldBuyRandomCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getRandomCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldBuyRandomCardHolderInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getRandomCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldBuyWithoutCVCInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldBuyWithoutCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }


    @Test
    void shouldBuyRandomCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getInvalidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldBuyRandomCVCInvalidCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getInvalidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }
}