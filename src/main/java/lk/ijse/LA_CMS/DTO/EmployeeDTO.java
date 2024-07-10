package lk.ijse.LA_CMS.DTO;

import lk.ijse.LA_CMS.Entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO extends Employee implements Serializable {
    private String id;
    private String name;
    private String position;
    private String address;
    private String email;
    private String contact;

}
