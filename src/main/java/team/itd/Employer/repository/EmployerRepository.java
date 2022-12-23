package team.itd.Employer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.itd.Employer.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
