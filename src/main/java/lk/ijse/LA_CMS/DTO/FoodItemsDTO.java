package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodItemsDTO implements Serializable {
    private String id;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
