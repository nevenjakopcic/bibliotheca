package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.model.Book;

public class BookDtoMapper {

    public static BookDto map(Book source, Long reservationId) {
        return BookDto.builder()
            .id(source.getId())
            .title(source.getTitle())
            .genre(GenreDtoMapper.map(source.getGenre()))
            .author(AuthorDtoMapper.map(source.getAuthor()))
            .description(source.getDescription())
            .reservationId(reservationId)
            .build();
    }

    private BookDtoMapper() {}
}
