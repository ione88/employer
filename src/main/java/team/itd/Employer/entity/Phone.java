package team.itd.Employer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone", schema = "employers")
public class Phone {
    @Id
    private Long id;
    private String country;
    private String city;
    private String number;
    private String comment;
}
