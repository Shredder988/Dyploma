package ru.netology.TourPaymentService.dataBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestEntity {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}
