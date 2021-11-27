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
public class AuthorDto implements Serializable {

    private static final long serialVersionUID = -8534567741751263738L;

    private Long id;
    private String name;
}
