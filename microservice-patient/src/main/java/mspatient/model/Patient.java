package mspatient.model;


import jakarta.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name ="date_of_birth")
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name ="address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;


}
