package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.BookReservationDto;
import hr.njakopcic.bibliotheca.model.BookReservation;

public class BookReservationDtoMapper {

    public static BookReservationDto map(BookReservation source) {
        return BookReservationDto.builder()
            .id(source.getId())
            .book(BookDtoMapper.map(source.getBook()))
            .borrower(UserDtoMapper.map(source.getBorrower()))
            .borrowedDate(source.getBorrowedDate())
            .dueDate(source.getDueDate())
            .returned(source.getReturned())
            .build();
    }

    private BookReservationDtoMapper() {}
}
