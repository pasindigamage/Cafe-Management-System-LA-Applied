package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplier {
    private String supplierId;
    private String inventoryId;
    private Date date;
}