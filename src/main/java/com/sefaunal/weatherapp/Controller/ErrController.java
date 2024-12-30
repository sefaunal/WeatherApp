package com.sefaunal.weatherapp.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest){
        Object Status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Status != null){
            Integer StatusCode = Integer.valueOf(Status.toString());

            if (StatusCode == HttpStatus.NOT_FOUND.value()){
                return "Error404";
            }
            else if (StatusCode == HttpStatus.FORBIDDEN.value()){
                return "Error403";
            }
        }
        return "Error";
    }
}
