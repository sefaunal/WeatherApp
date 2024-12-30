package com.sefaunal.weatherapp.Controller;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public RedirectView redirectHome(){
        return new RedirectView("/home");
    }

    @GetMapping("/error403")
    public String Error403(){
        return "Error403";
    }

    @GetMapping("/home")
    public ModelAndView homePage(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("user", null);
            return new ModelAndView("HomePage");
        }
        else {
            User user = userService.findByUserMail(principal.getName());
            if (user.getUserRole().equals("BANNED")){
                return new ModelAndView("ErrorBan");
            }else {
                model.addAttribute("user", user);
                return new ModelAndView("HomePage");
            }
        }
    }

    @GetMapping("/login")
    public String LoginPage(HttpServletResponse httpServletResponse, Principal principal){
        if (principal != null){
            try {
                httpServletResponse.sendRedirect("/home");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return "LoginPage";
    }

    @PostMapping("/register")
    public @ResponseBody Boolean createAccount(@RequestParam String userName,@RequestParam String userSurname, @RequestParam String userPassword, @RequestParam String userMail, @RequestParam String userImageURL){
        User user = new User();
        user.setUserMail(userMail);
        user.setUserName(userName);
        user.setUserSurname(userSurname);
        user.setUserPassword(userPassword);
        user.setUserImageURL(userImageURL);
        user.setUserRole("USER");

        LocalDateTime localDateTime = LocalDateTime.now();
        user.setUserCreationDate(localDateTime);

        try {
            user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return userService.createUser(user);
    }

    @PostMapping("/register/checkMail")
    public @ResponseBody Boolean checkMail(@RequestParam String userMail){
        return userService.findByUserMail(userMail) == null;
    }
}
