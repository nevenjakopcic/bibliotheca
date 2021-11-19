package hr.njakopcic.bibliotheca.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @NotBlank
    private String title;

    @NotNull
    private Long genreId;

    @NotNull
    private Long authorId;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate publishDate;
}
