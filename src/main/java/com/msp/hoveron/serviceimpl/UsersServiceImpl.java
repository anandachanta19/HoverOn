package com.msp.hoveron.serviceimpl;

import com.msp.hoveron.entity.Users;
import com.msp.hoveron.exception.EmailExistsException;
import com.msp.hoveron.payload.UsersDto;
import com.msp.hoveron.repository.UsersRepository;
import com.msp.hoveron.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UsersDto signup(UsersDto userDto) throws EmailExistsException {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException("Email already exists");
        }

        if (userDto.getAge() == null || userDto.getAge() < 18) {
            throw new IllegalArgumentException("Age must be at least 18");
        }
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUserName(userDto.getUserName());
        user.setGender(userDto.getGender());
        user.setAge(userDto.getAge());
        user = userRepository.save(user);
        return userDto;
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Users getUserById(int userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Users findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public Users findByUserId(Long userId) {
        return userRepository.findByUserId(Math.toIntExact(userId));
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }


    private Users usersDtoToEntity(UsersDto usersDto) {
        Users users = new Users();
        users.setUserName(usersDto.getUserName());
        users.setPassword(usersDto.getPassword());
        users.setEmail(usersDto.getEmail());
        users.setGender(usersDto.getGender());
        users.setAge(usersDto.getAge());
        return users;
    }

    private UsersDto entityToDto(Users users) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserName(users.getUserName());
        usersDto.setPassword(users.getPassword());
        usersDto.setEmail(users.getEmail());
        usersDto.setGender(users.getGender());
        usersDto.setAge(users.getAge());
        return usersDto;
    }

}
