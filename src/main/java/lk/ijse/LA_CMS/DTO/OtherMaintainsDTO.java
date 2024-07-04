package lk.ijse.LA_CMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OtherMaintainsDTO implements Serializable {
    private String id;
    private String description;
    private Date date;
    private String amount;
}
