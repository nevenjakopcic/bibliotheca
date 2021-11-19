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
public class GenreDto implements Serializable {

    private static final long serialVersionUID = -1225524187560833624L;

    private Long id;
    private String name;
}
