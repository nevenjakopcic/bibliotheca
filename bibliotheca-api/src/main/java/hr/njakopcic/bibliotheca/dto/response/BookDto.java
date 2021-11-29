package hr.njakopcic.bibliotheca.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    private static final long serialVersionUID = -6616574079094780345L;

    private Long id;
    private String title;
    private GenreDto genre;
    private AuthorDto author;
    private String description;
    private Long reservationId;
}
