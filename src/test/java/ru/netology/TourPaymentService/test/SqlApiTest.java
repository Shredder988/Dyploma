package ru.netology.TourPaymentService.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.TourPaymentService.data.APIHelper;
import ru.netology.TourPaymentService.data.DataHelper;
import ru.netology.TourPaymentService.data.SQLHelper;


public class SqlApiTest extends BaseTest {
    @Test
    @DisplayName("Оплата разрешённой картой, статус в базе данных 'APPROVED'")
    @Epic("Оплата картой")
    @Feature("Оплата разрешенной картой")
    @Story("Статус опеоации в БД")

    void shouldSuccessStatusIfPaymentByApprovedCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        APIHelper.cardPayment(cardInfo);
        var paymentCardData = SQLHelper.getPaymentsEntityData();

        Assertions.assertEquals("APPROVED", paymentCardData.getStatus());
    }

    @Test
    @DisplayName("Оплата запрещенной картой, статус в базе данных 'DECLINED'")
    @Epic("Оплата картой")
    @Feature("Оплата запрещенной картой")
    @Story("Статус опеоации в БД")
    void shouldFiledStatusIfPaymentByDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        APIHelper.cardPayment(cardInfo);
        var paymentCardData = SQLHelper.getPaymentsEntityData();

        Assertions.assertEquals("DECLINED", paymentCardData.getStatus());
    }

    @Test
    @DisplayName("Оплата в кредит разрешенной картой, статус в базе данных APPROVED")
    @Epic("Оплата в кредит")
    @Feature("Оплата разрешенной картой")
    @Story("Статус опеоации в БД")
    void shouldFieldStatusIfPaymentByCreditApprovedCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        APIHelper.creditPayment(cardInfo);
        var paymentCreditData = SQLHelper.getCreditRequestEntityData();

        Assertions.assertEquals("APPROVED", paymentCreditData.getStatus());
    }

    @Test
    @DisplayName("Оплата в кредит запрещенной картой, статус в базе данных DECLINED")
    @Epic("Оплата в кредит")
    @Feature("Оплата запрещенной картой")
    @Story("Статус опеоации в БД")
    void shouldFieldStatusIfPaymentByCreditDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        APIHelper.creditPayment(cardInfo);
        var paymentCreditData = SQLHelper.getCreditRequestEntityData();

        Assertions.assertEquals("DECLINED", SQLHelper.getCreditRequestEntityData().getStatus());
    }

    @Test
    @DisplayName("Оплата картой , в таблицу order_entity заносится ID транзакции в поле 'payment_id'")
    @Epic("Оплата картой")
    @Story("Корректность занесения данных в таблицу order_entity")

    void ShouldTransactionIdWrittenInPaymentIdField() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        APIHelper.cardPayment(cardInfo);

        var transactionId = SQLHelper.getPaymentsEntityData().getTransaction_id();
        var orderEntityData = SQLHelper.getOrderEntityData();

        Assertions.assertEquals(transactionId, orderEntityData.getPayment_id());
    }

    @Test
    @DisplayName("Оплата в кредит, в в таблицу order_entity  в поле credit_id заносится значение из bank_id таблицы credit_request_entity)")
    @Epic("Оплата в кредит")
    @Story("Корректность занесения данных в таблицу order_entity")
    void ShouldFieldValueCreditIdWrittenInFieldCreditId() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        APIHelper.creditPayment(cardInfo);

        var bankId = SQLHelper.getCreditRequestEntityData().getBank_id();
        var orderEntityData = SQLHelper.getOrderEntityData();

        Assertions.assertEquals(bankId, orderEntityData.getCredit_id());
    }

    @Test
    @DisplayName("Оплата картой отсутствующей в системе, Данные не заносятся в БД")
    @Epic("Оплата картой")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Отсутствие новых данных в столицах ")
    void ShouldNotAmountRowsWhenPaymentFiledCard() {
        var cardInfo = DataHelper.getFiledCardInfo();
        var startRowsAmountInPaymentEntity = SQLHelper.getRowsAmountFromPaymentEntityTable();
        var startRowsAmountInOrderEntity = SQLHelper.getRowAmountFromOrderEntityTable();

        APIHelper.cardPaymentWhenUsedFiledCard(cardInfo);

        var currentRowsAmountInPaymentEntity = SQLHelper.getRowsAmountFromPaymentEntityTable();
        var currentRowsAmountInOrderEntity = SQLHelper.getRowAmountFromOrderEntityTable();

        Assertions.assertEquals(startRowsAmountInPaymentEntity, currentRowsAmountInPaymentEntity);
        Assertions.assertEquals(startRowsAmountInOrderEntity, currentRowsAmountInOrderEntity);
    }
    @Test
    @DisplayName("Кредит по карте отсутствующей в системе, данные не заносятся в БД")
    @Epic("Оплата в кредит")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Отсутствие новых данных в столицах ")
    void ShouldNotAmountRowsWhenPaymentByCreditFiledCard(){
        var cardInfo = DataHelper.getFiledCardInfo();
        var startRowsAmountInCreditRequestEntity = SQLHelper.getRowAmountFromCreditRequestEntityTable();
        var startRowsAmountInOrderEntity = SQLHelper.getRowAmountFromOrderEntityTable();


        APIHelper.creditPaymentWhenUsedFiledCard(cardInfo);

        var currentRowsAmountInCreditRequestEntity = SQLHelper.getRowAmountFromCreditRequestEntityTable();
        var currentRowsAmountInOrderEntity = SQLHelper.getRowAmountFromOrderEntityTable();


        Assertions.assertEquals(startRowsAmountInCreditRequestEntity,currentRowsAmountInCreditRequestEntity);
        Assertions.assertEquals(startRowsAmountInOrderEntity, currentRowsAmountInOrderEntity);
        }


}
