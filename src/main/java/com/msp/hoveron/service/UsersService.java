package com.msp.hoveron.service;

import com.msp.hoveron.entity.Users;
import com.msp.hoveron.exception.EmailExistsException;
import com.msp.hoveron.payload.UsersDto;

public interface UsersService {
    public UsersDto signup(UsersDto usersDto) throws EmailExistsException;

    Users findByUsername(String username);

    Users getUserById(int userId);

    Users findByUserNameAndPassword(String userName, String password);

    Users findByUserId(Long userId);

    void save(Users user);
}
