package com.sefaunal.weatherapp.Service;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean createUser(User user){
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User findByUserMail(String mail){
        return userRepository.findByUserMail(mail);
    }

    public List<User> findByUserRole(String role){
        return userRepository.findByUserRole(role);
    }

    public void deleteUser(Long userID){
        userRepository.deleteById(userID);
    }

    public User findById(Long userID){
        return userRepository.findById(userID).get();
    }
}
