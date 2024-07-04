package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KitchenWareMaintainsDTO implements Serializable {
    private String id;
    private String kitchenWareId;
    private String description;
    private String date;
    private String amount;
}
