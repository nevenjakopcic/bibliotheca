package hr.njakopcic.bibliotheca.dto.response;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookReservationDto implements Serializable {

    private static final long serialVersionUID = 7925006164893345923L;

    private Long id;
    private BookDto book;
    private UserDto borrower;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private Boolean returned;
}
