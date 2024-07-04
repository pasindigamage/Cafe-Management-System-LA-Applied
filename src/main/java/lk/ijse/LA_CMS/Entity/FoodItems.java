package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodItems {
    private String id;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
