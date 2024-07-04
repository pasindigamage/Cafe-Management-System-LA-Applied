package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee extends Supplier {
    private String id;
    private String name;
    private String position;
    private String address;
    private String email;
    private String contact;
}
