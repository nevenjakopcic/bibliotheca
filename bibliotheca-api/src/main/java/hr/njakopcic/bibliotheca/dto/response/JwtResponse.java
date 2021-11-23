package hr.njakopcic.bibliotheca.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -4874349840189375939L;

    private final Long id;
    private final String jwtToken;
    private final String email;
    private final Integer role;
}
