package msclientui.beans;

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
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    private GenderBeans gender;
    private String address;
    private String phoneNumber;


}
