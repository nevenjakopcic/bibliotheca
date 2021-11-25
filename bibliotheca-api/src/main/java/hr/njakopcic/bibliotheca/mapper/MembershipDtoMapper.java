package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.MembershipDto;
import hr.njakopcic.bibliotheca.model.Membership;

public class MembershipDtoMapper {

    public static MembershipDto map(Membership source) {
        return MembershipDto.builder()
            .id(source.getId())
            .dateCreated(source.getDateCreated())
            .lastUpdated(source.getLastUpdated())
            .validUntil(source.getValidUntil())
            .build();
    }

    private MembershipDtoMapper() {}
}
