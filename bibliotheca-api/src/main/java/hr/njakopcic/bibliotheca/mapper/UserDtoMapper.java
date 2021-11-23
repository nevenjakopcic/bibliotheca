package hr.njakopcic.bibliotheca.mapper;

import hr.njakopcic.bibliotheca.dto.response.UserDto;
import hr.njakopcic.bibliotheca.model.User;

public class UserDtoMapper {

    public static UserDto map(User source) {
        return UserDto.builder()
            .id(source.getId())
            .username(source.getUsername())
            .email(source.getEmail())
            .role(source.getRole().ordinal()).build();
    }

    private UserDtoMapper() {}
}
