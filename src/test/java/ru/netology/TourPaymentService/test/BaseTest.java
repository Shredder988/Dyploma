package ru.netology.TourPaymentService.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.TourPaymentService.data.DataHelper;
import ru.netology.TourPaymentService.data.SQLHelper;


public class BaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SQLHelper.cleanDatabase();
    }


}
