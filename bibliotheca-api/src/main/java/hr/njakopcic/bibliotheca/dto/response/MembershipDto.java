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
public class MembershipDto implements Serializable {

    private static final long serialVersionUID = 7988123399319752381L;

    private Long id;
    private LocalDate dateCreated;
    private LocalDate lastUpdated;
    private LocalDate validUntil;
}
