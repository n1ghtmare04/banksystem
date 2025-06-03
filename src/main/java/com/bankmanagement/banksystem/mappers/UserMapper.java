package com.bankmanagement.banksystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bankmanagement.banksystem.dto.RegisterUserRequest;
import com.bankmanagement.banksystem.dto.UserDto;
import com.bankmanagement.banksystem.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "userId", ignore = true)
    User toEntity(RegisterUserRequest request);
}
