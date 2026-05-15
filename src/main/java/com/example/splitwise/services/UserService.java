package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UserAlreadyExistsException;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String password, String phone) throws UserAlreadyExistsException {

        Optional<User> optionalUser = userRepository.findByPhone(phone);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("Phone Number already Registered!");
        }

        User user = new User();
        user.setName(name);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setPhone(phone);
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public void deleteUser(int userId) throws InvalidUserException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User does not Exist"));

        userRepository.delete(user);
    }

}
