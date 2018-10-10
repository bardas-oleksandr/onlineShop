package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderCreateDto implements Serializable {

    private static final long serialVersionUID = -7382801572605686420L;

    @Min(value = 1, message = "unacceptable_user_id")
    private int userId;

    @Size(max = 255, message = "unacceptable_address_length")
    @NotEmpty(message = "empty_address")
    private String address;

    @NotNull(message = "empty_order_date")
    private Timestamp date;

    @Min(value = 0, message = "unexpected_payment_conditions")
    @Max(value = 1, message = "unexpected_payment_conditions")
    private int paymentConditionsIndex;

    public OrderCreateDto(int userId, String address, Timestamp date, int paymentConditionsIndex) {
        this.userId = userId;
        this.address = address;
        this.date = date;
        this.paymentConditionsIndex = paymentConditionsIndex;
    }
}