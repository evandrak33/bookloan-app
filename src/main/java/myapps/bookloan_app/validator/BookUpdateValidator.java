package myapps.bookloan_app.validator;

import myapps.bookloan_app.dto.BookUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class BookUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BookUpdateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookUpdateDTO dto = (BookUpdateDTO) target;

        if (dto.getId() == null) {
            errors.rejectValue("id", "book.id.missing", "Book ID is missing");
        }

        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            errors.rejectValue("title", "book.title.empty", "Title is required");
        }

        if (dto.getAuthor() == null || dto.getAuthor().trim().isEmpty()) {
            errors.rejectValue("author", "book.author.empty", "Author is required");
        }

        if (dto.getPublisher() == null || dto.getPublisher().trim().isEmpty()) {
            errors.rejectValue("publisher", "book.publisher.empty", "Publisher is required");
        }

        if (dto.getGenre() == null || dto.getGenre().trim().isEmpty()) {
            errors.rejectValue("genre", "book.genre.empty", "Genre is required");
        }

        int currentYear = Year.now().getValue();
        if (dto.getPublicationYear() < 1000 || dto.getPublicationYear() > currentYear) {
            errors.rejectValue(
                    "publicationYear",
                    "book.year.invalid",
                    "Publication year must be a valid year"
            );
        }
    }
}
