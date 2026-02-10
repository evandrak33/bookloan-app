package myapps.bookloan_app.repository;

import myapps.bookloan_app.model.Loan;
import myapps.bookloan_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    long countByUser(User user);
}

