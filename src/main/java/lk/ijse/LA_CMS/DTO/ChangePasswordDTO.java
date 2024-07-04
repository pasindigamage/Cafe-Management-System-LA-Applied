package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ChangePasswordDTO implements Serializable {
    String userName;
    String id;
    String newPassword;
}
