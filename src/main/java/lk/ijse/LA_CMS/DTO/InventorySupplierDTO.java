package lk.ijse.LA_CMS.DTO;

import lk.ijse.LA_CMS.Entity.InventorySupplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDTO extends InventorySupplier implements Serializable {
    private String supplierId;
    private String inventoryId;
    private Date date;
}