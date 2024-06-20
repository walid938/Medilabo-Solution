package msdiabeterisk.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteBeans {
    private String id;
    private int patientId;
    private String patientName;
    private String noteContent;

}
