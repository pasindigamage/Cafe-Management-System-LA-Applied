package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO implements Serializable {
    private String id;
    private String nic;
    private String name;
    private String companyAddress;
    private String email;
    private String contact;
}
