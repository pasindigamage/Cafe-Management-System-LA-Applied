package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class KitchenWareMaintains {
    private String id;
    private String kitchenWareId;
    private String description;
    private String date;
    private String amount;
}
