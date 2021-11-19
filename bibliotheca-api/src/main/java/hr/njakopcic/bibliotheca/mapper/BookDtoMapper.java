package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.model.Book;

public class BookDtoMapper {

    public static BookDto map(Book source) {
        return BookDto.builder()
            .id(source.getId())
            .title(source.getTitle())
            .genre(GenreMapper.map(source.getGenre()))
            .author(AuthorMapper.map(source.getAuthor()))
            .description(source.getDescription())
            .publishDate(source.getPublishDate())
            .build();
    }

    private BookDtoMapper() {}
}
