package ru.netology.TourPaymentService.dataBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    private String id;
    private int amount;
    private String created;
    private String status;
    private String transaction_id;
}
