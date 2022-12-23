package team.itd.Employer.service;

import org.springframework.stereotype.Service;
import team.itd.Employer.entity.Employer;
import team.itd.Employer.repository.EmployerRepository;

import java.util.Optional;

@Service
public class EmployerService {
    private EmployerRepository employerRepository;

    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public void update(Employer employer) {
        Optional<Employer> existEmployer = employerRepository.findById(employer.getId());
        employer = merge(existEmployer, employer);
        employerRepository.save(employer);
    }

    private Employer merge(Optional<Employer> existEmployer, Employer employer) {
        if (existEmployer.isEmpty()) {
            return employer;
        }
        //TODO: add merge logic
        return existEmployer.get();
    }
}
