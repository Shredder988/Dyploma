
package ru.netology.TourPaymentService.test;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.TourPaymentService.data.DataHelper;
import ru.netology.TourPaymentService.page.MainPage;


import static com.codeborne.selenide.Selenide.open;

public class CreditPaymentTest extends BaseTest{
    @Test
    @DisplayName("Оплата в кредит разрешенной картой, отображается сообщение об подтверждении банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата разрешенной картой")
    @Story("Всплывающее окно со статеумом операции")
    void ShouldSuccessMessageIfPaymentByCreditApprovedCard(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataHelper.getApprovedCardInfo());

        creditPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Оплата в кредит запрещенной картой, отображается сообщение об отказе банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата запрещенной картой")
    @Story("Всплывающее окно со статеумом операции")
    void ShouldErrorMessageIfPaymentByCreditDeclinedCard(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataHelper.getDeclinedCardInfo());

        creditPaymentPage.shouldNotificationSuccessMessage("Банк отказал в проведении операции");
    }

    @Test
    @DisplayName("Оплата в кредит картой отсутствующей в системе. Отображается сообщение об отказе банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Всплывающее окно со статеумом операции")
    void ShouldErrorMsg(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();
        creditPaymentPage.pay(DataHelper.getFiledCardInfo());

        creditPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }




}
