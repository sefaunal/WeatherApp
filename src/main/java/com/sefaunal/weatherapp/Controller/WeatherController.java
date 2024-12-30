package com.sefaunal.weatherapp.Controller;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Model.Weather;
import com.sefaunal.weatherapp.Service.UserService;
import com.sefaunal.weatherapp.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class WeatherController {
    @Autowired
    UserService userService;

    @Autowired
    WeatherService weatherService;

    @PostMapping("/home/weather/save")
    public @ResponseBody Boolean createWeather(@RequestBody Weather weather, Principal principal){
        User user = userService.findByUserMail(principal.getName());
        try {
            weather.setUser(user);
            user.getWeatherList().add(weather);
            userService.createUser(user);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @GetMapping("/home/weatherRecords")
    public ModelAndView weatherRecords(Principal principal, Model model,
                                       @RequestParam("page") int currentPage,
                                       @RequestParam("sortby") String field,
                                       @RequestParam("sortdir") String sortDir){
        User user = userService.findByUserMail(principal.getName());

        field = WeatherField(field);
        Page<Weather> page = weatherService.findWithSorting(field, sortDir, currentPage, user);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Weather> weatherList = page.getContent();
        field = WeatherFieldReverse(field);

        model.addAttribute("sortBy", field);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        model.addAttribute("user", user);
        model.addAttribute("weatherList", weatherList);
        return new ModelAndView("WeatherList");
    }

    @GetMapping("/home/weatherRecords/delete")
    public RedirectView deleteWeather(Principal principal, @RequestParam Long weatherID,
                                      @RequestParam("page") int currentPage,
                                      @RequestParam("sortby") String field,
                                      @RequestParam("sortdir") String sortDir){

        User user = userService.findByUserMail(principal.getName());
        if (weatherService.findByWeatherID(weatherID).getUser() == user){
            weatherService.deleteByWeatherID(weatherID);
            String URL = "/home/weatherRecords?page=" + currentPage + "&sortby=" + field + "&sortdir=" + sortDir;
            return new RedirectView(URL);
        }
        else{
            return new RedirectView("/error403");
        }
    }

    private String WeatherField(String field){
        switch (field){
            case "status": return "weatherStatus";
            case "temp": return "weatherTemp";
            case "wind": return "weatherWind";
            case "humidity": return "weatherHumidity";
            case "pressure": return "weatherPressure";
            case "city": return "weatherCity";
            case "country": return "weatherCountry";
            default: return "weatherDate";
        }
    }

    private String WeatherFieldReverse(String field){
        switch (field){
            case "weatherStatus": return "status";
            case "weatherTemp": return "temp";
            case "weatherWind": return "wind";
            case "weatherHumidity": return "humidity";
            case "weatherPressure": return "pressure";
            case "weatherCity": return "city";
            case "weatherCountry": return "country";
            default: return "date";
        }
    }
}
