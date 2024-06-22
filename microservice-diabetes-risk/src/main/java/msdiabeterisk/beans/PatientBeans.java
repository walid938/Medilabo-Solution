package msdiabeterisk.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientBeans {
    private int id;
    private LocalDate dateOfBirth;
    private GenderBeans gender;

    public PatientBeans(int patientId, String jane, String doe, LocalDate of, GenderBeans genderBeans) {
    }
}
