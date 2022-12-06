package ru.netology.TourPaymentService.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.TourPaymentService.data.DataHelper;
import ru.netology.TourPaymentService.page.MainPage;
import ru.netology.TourPaymentService.page.PaymentsPage;

import static com.codeborne.selenide.Selenide.open;

public class FieldValidationTest extends BaseTest {

    @Test
    @DisplayName("Оплата картой, дата действия карты равна текущей, отображается сообщение об одобрении банком")
    @Epic("Валидация полей")
    @Features({@Feature("Поле года"), @Feature("Поле месяца")})
    @Story("Нижняя  срока действия")
    void shouldSuccessMessageIfDateIsCurrent() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getCurrentDatePlusMonths(0));

        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Оплата картой, дата действия в верхней границе (текущая дата + 5 лет), отображается сообщение об одобрении банком")
    @Epic("Валидация полей")
    @Features({@Feature("Поле года"), @Feature("Поле месяца")})
    @Story("Верхняя граница срока действия")
    void shouldErrorMessageIfDateInUpperBorder() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getCurrentDatePlusMonths(60));

        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Оплата картой, дата действия выше верхней границе (5 лет + 1 месяц), заявка не отправляется, ошибка валидации поля 'Неверно указан срок действия карты' ")
    @Epic("Валидация полей")
    @Feature("Поле года")
    @Story("Выще верхней  границы срока действия")
    void shouldValidationErrorCardValidityDateFieldPlus61Month() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getCurrentDatePlusMonths(61));

        cardPaymentPage.shouldYearFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Оплата картой, дата действия выше верхней границе (5 лет + 2 месяца), заявка не отправляется, ошибка валидации поля 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле года")
    @Story("Выще верхней  границы срока действия")
    void shouldValidationErrorCardValidityDateFieldPlus62Months() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getCurrentDatePlusMonths(62));

        cardPaymentPage.shouldYearFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Оплата картой, дата действия меньше текущей границе, заявка не отправляется, ошибка валидации поля 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Ниже нижней  границы срока действия")
    void shouldValidationErrorIfCardValidityDateLessCurrent() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getCurrentDatePlusMonths(-1));

        cardPaymentPage.shouldMonthFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Оплата картой с нулевым месяцем, ошибка валидации поля месяца. 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Поле месяца со знанчением '00' ")
    void shouldValidationErrorErrorIfValueOfTheMonthsIsZero() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();


        creditPaymentPage.pay(DataHelper.getZeroMonthCardInfo());

        creditPaymentPage.shouldMonthFieldSubErrorMsg("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("Оплата в кредит с пустым полем номера карты, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле номера карты")
    @Story("Пустое поле")
    void shouldValidationErrorCardNumberFieldIfValueIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataHelper.getEmptyCardNumberCardNumberInfo());

        creditPaymentPage.shouldCardNumberFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Оплата картой с пустым полем месяца, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Пустое поле")
    void shouldValidationErrorMonthFieldErrorIfValueIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getEmptyMonthCardInfo());

        cardPaymentPage.shouldMonthFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Оплата картой с пустым полем года, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле года")
    @Story("Пустое поле")
    void shouldValidationErrorIfYearFieldIsEmpty(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getEmptyYearCardInfo());

        cardPaymentPage.shouldYearFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Оплата в кредит с пустым полем имени, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле имени")
    @Story("Пустое поле")
    void shouldValidationErrorIfHolderFieldIsEmpty(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataHelper.getEmptyHolderCardInfo());

        creditPaymentPage.shouldHolderFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Оплата картой с пустым полем CVC, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле CVC")
    @Story("Пустое поле")
    void shouldValidationErrorIfCvvFieldIsEmpty(){
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataHelper.getEmptyCvcCardInfo());

        cardPaymentPage.shouldCvcFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Оплата с использованием имени кириллицей, ошибка валидация поля 'Неверный формат'")
    @Epic("Валидация полей")
    @Feature("Поле имени")
    @Story("Имя киррилицей")
        void ShouldValidationErrorIfHolderNameIsCyrillic(){
            var mainPage = open("http://localhost:8080", MainPage.class);
            var cardPaymentPage = mainPage.paymentByCard();

            cardPaymentPage.pay(DataHelper.getCyrillicHolderNameCardInfo());

            cardPaymentPage.shouldHolderFieldSubErrorMsg("Неверный формат");
        }
}
