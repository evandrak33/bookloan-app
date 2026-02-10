package myapps.bookloan_app.validator;

import myapps.bookloan_app.dto.LoanCreateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoanCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LoanCreateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoanCreateDTO dto = (LoanCreateDTO) target;

        if (dto.getBookId() == null) {
            errors.rejectValue("bookId", "loan.bookId.missing", "Book is required");
        }

        if (dto.getUserId() == null) {
            errors.rejectValue("userId", "loan.userId.missing", "User is required");
        }
    }
}
