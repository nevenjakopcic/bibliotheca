package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.GenreDto;
import hr.njakopcic.bibliotheca.model.Genre;

public class GenreDtoMapper {

    public static GenreDto map(Genre source) {
        return GenreDto.builder()
            .id(source.getId())
            .name(source.getName())
            .build();
    }

    private GenreDtoMapper() {}
}
