package com.sefaunal.weatherapp.Controller;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/admin/userList")
    public ModelAndView viewUsers(Model model, Principal principal){
        List<User> userList = userService.findByUserRole("USER");
        User currentUser = userService.findByUserMail(principal.getName());

        model.addAttribute("user", currentUser);
        model.addAttribute("userList", userList);
        model.addAttribute("ViewType", "USER");

        return new ModelAndView("UserList");
    }

    @GetMapping("/admin/adminList")
    public ModelAndView viewAdmin(Model model, Principal principal){
        List<User> adminList = userService.findByUserRole("ADMIN");
        User currentUser = userService.findByUserMail(principal.getName());

        model.addAttribute("user", currentUser);
        model.addAttribute("userList", adminList);
        model.addAttribute("ViewType", "ADMIN");

        return new ModelAndView("UserList");
    }

    @GetMapping("/admin/bannedUsers")
    public ModelAndView viewBannedUsers(Model model, Principal principal){
        List<User> bannedUserList = userService.findByUserRole("BANNED");
        User currentUser = userService.findByUserMail(principal.getName());

        model.addAttribute("user", currentUser);
        model.addAttribute("userList", bannedUserList);
        model.addAttribute("ViewType", "BANNED");

        return new ModelAndView("UserList");
    }

    @GetMapping("/admin/banUser")
    public RedirectView banUser(@RequestParam Long userID, @RequestParam String ViewType){
        User user = userService.findById(userID);
        user.setUserRole("BANNED");
        if (ViewType.equals("USER")){
            userService.createUser(user);
            return new RedirectView("/admin/userList");
        }else {
            userService.createUser(user);
            return new RedirectView("/admin/adminList");
        }
    }

    @GetMapping("/admin/removeBan")
    public RedirectView removeBan(@RequestParam Long userID){
        User user = userService.findById(userID);
        user.setUserRole("USER");
        userService.createUser(user);
        return new RedirectView("/admin/bannedUsers");
    }

    @GetMapping("/admin/deleteUser")
    public RedirectView deleteUser(@RequestParam Long userID){
        userService.deleteUser(userID);
        return new RedirectView("/admin/bannedUsers");
    }
}
