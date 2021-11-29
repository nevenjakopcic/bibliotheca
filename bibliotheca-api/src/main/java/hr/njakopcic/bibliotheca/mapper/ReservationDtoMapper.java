package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.ReservationDto;
import hr.njakopcic.bibliotheca.model.Reservation;

public class ReservationDtoMapper {

    public static ReservationDto map(Reservation source) {
        return ReservationDto.builder()
            .id(source.getId())
            .book(BookDtoMapper.map(source.getBook(), source.getId()))
            .borrower(UserDtoMapper.map(source.getBorrower()))
            .borrowedDate(source.getBorrowedDate())
            .dueDate(source.getDueDate())
            .returned(source.getReturned())
            .build();
    }

    private ReservationDtoMapper() {}
}
