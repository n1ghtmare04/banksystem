package com.bankmanagement.banksystem.mappers;

import com.bankmanagement.banksystem.dto.RegisterUserRequest;
import com.bankmanagement.banksystem.dto.UserDto;
import com.bankmanagement.banksystem.entities.Role;
import com.bankmanagement.banksystem.entities.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-03T15:29:05+0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.contactNumber( user.getContactNumber() );
        userDto.email( user.getEmail() );
        userDto.employed( user.getEmployed() );
        userDto.isAdmin( user.getIsAdmin() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userDto.roles( new LinkedHashSet<Role>( set ) );
        }
        userDto.totalLoans( user.getTotalLoans() );
        userDto.userBalance( user.getUserBalance() );
        userDto.userId( user.getUserId() );
        userDto.userLogin( user.getUserLogin() );
        userDto.userName( user.getUserName() );
        userDto.yearsCountry( user.getYearsCountry() );

        return userDto.build();
    }

    @Override
    public User toEntity(RegisterUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.contactNumber( request.getContactNumber() );
        user.email( request.getEmail() );
        user.employed( request.getEmployed() );
        user.isAdmin( request.getIsAdmin() );
        Set<Role> set = request.getRoles();
        if ( set != null ) {
            user.roles( new LinkedHashSet<Role>( set ) );
        }
        user.totalLoans( request.getTotalLoans() );
        user.userBalance( request.getUserBalance() );
        user.userLogin( request.getUserLogin() );
        user.userName( request.getUserName() );
        user.userPassword( request.getUserPassword() );
        user.yearsCountry( request.getYearsCountry() );

        return user.build();
    }
}
