package com.sefaunal.WeatherApp.Controller;

import com.sefaunal.WeatherApp.Model.User;
import com.sefaunal.WeatherApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
}
