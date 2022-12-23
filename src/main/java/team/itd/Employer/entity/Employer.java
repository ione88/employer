package team.itd.Employer.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "employer", schema = "employers")
public class Employer {
    @Id
    private Long id;
    @EqualsAndHashCode.Exclude private String url;
    @EqualsAndHashCode.Exclude private String alternativeUrl;
    @EqualsAndHashCode.Exclude private String name;
    @OneToOne
    @JoinColumn(name = "id")
    @EqualsAndHashCode.Exclude private Contact contact;
    @EqualsAndHashCode.Exclude private String logo;
    @EqualsAndHashCode.Exclude private String site_url;
    public String csvRow() {
        return String.format("%s;=image(\"%s\");%s;%s;",name,logo,alternativeUrl,site_url);
    }

}

