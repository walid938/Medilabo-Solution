package msclientui.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoteBeans {
    private String id;
    private int patientId;
    private String patientName;
    private String noteContent;


}
