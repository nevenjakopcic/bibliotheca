package hr.njakopcic.bibliotheca.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUsernameRequest {

    @NotBlank(message = "Field 'newUsername' cannot be blank.")
    private String newUsername;
}
