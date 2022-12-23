package team.itd.Employer.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact", schema = "employers")
public class Contact {
    @Id
    private Long id;
    private String name;
    private String email;
    @OneToOne
    @JoinColumn(name = "id")
    private Phone phone;
}
