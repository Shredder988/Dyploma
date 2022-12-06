package ru.netology.TourPaymentService.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.TourPaymentService.data.DataHelper;
import ru.netology.TourPaymentService.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class CardPaymentTest extends BaseTest{
    @Test
    @DisplayName("Покупка разрешенной картой, отображается сообщение об одобрении банком")
    @Epic("Оплата картой")
    @Feature("Оплата разрешенной картой")
    @Story("Всплывающее окно со статеумом операции")
    void shouldSuccessMessageIfPaymentOfCard() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getApprovedCardInfo());
        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Покупка запрещенной картой. Отображается сообщение об отказе банком")
    @Epic("Оплата картой")
    @Feature("Оплата запрещенной картой")
    @Story("Всплывающее окно со статеумом операции")
    void shouldErrorMessageIfPaymentOfDeclinedCard(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getDeclinedCardInfo());

        cardPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }

    @Test
    @DisplayName("Покупка картой отсутствующей в системе. Отображается сообщение об отказе банком")
    @Epic("Оплата картой")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Всплывающее окно со статеумом операции")
    void ShouldErrorMsg(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();
        cardPaymentPage.pay(DataHelper.getFiledCardInfo());

        cardPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }

}
