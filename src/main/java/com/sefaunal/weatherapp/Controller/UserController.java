package com.sefaunal.weatherapp.Controller;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ModelAndView profilePage(Principal principal, Model model) {
        User currentUser = userService.findByUserMail(principal.getName());

        model.addAttribute("user", currentUser);
        return new ModelAndView("ProfilePage");
    }

    @PostMapping("/profile/updateProfile")
    public @ResponseBody String updateProfile(Principal principal, @RequestParam Long userID, @RequestParam String userName,
                                              @RequestParam String userSurname, @RequestParam String userMail,
                                              @RequestParam String userImage, @RequestParam String userBIO, Authentication authentication) {
        User currenUser = userService.findByUserMail(principal.getName());
        boolean changedMail = !currenUser.getUserMail().equals(userMail);

        if (currenUser.getUserID() == userID) {
            if (!userImage.equals("")) {
                currenUser.setUserImageURL(userImage);
            }
            currenUser.setUserName(userName);
            currenUser.setUserSurname(userSurname);
            currenUser.setUserMail(userMail);
            currenUser.setUserBIO(userBIO);

            try {
                userService.createUser(currenUser);
                return changedMail ? "diffMail" : "sameMail";
            }catch (Exception e) {
                return e.getMessage();
            }
        }

        else {
            return "Error403";
        }
    }

    @PostMapping("/profile/updatePassword")
    public @ResponseBody String updatePassword(Principal principal, @RequestParam Long userID, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User currentUser = userService.findByUserMail(principal.getName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (currentUser.getUserID() == userID) {
            if (bCryptPasswordEncoder.matches(oldPassword, currentUser.getUserPassword())) {
                currentUser.setUserPassword(new BCryptPasswordEncoder().encode(newPassword));

                try {
                    userService.createUser(currentUser);
                    return "success";
                }catch (Exception e) {
                    return e.getMessage();
                }
            }
            else {
                return "OldPasswordNotMatch";
            }
        }
        else {
            return "Error403";
        }
    }
}
