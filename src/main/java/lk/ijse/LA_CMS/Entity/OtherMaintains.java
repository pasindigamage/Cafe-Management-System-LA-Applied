package lk.ijse.LA_CMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OtherMaintains {
    private String id;
    private String description;
    private Date date;
    private String amount;
}
