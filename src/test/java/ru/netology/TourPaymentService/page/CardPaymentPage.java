package ru.netology.TourPaymentService.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;
    public class CardPaymentPage extends PaymentsPage {
        private CardPaymentPage() {
            $x("//h3[text()='Оплата по карте']").shouldBe(Condition.visible);
        }
}
