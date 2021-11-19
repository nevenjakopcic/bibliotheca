package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.AuthorDto;
import hr.njakopcic.bibliotheca.model.Author;

public class AuthorMapper {

    public static AuthorDto map(Author source) {
        return AuthorDto.builder()
            .id(source.getId())
            .name(source.getName())
            .description(source.getDescription())
            .build();
    }

    private AuthorMapper() {}
}
