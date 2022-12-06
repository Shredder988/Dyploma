package ru.netology.TourPaymentService.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class CreditPaymentPage extends PaymentsPage{
    private CreditPaymentPage() {
        $x("//h3[text()='Кредит по данным карты']").shouldBe(Condition.visible);
    }
}
