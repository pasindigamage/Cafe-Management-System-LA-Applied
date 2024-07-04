package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {
    private String id;
    private String nic;
    private String name;
    private String companyAddress;
    private String email;
    private String contact;
}
