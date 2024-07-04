package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailDTO implements Serializable {
        private String orderId;
        private String foodItemId;
        private int qty;
}
