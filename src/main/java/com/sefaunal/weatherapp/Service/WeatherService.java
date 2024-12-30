package com.sefaunal.weatherapp.Service;

import com.sefaunal.weatherapp.Model.User;
import com.sefaunal.weatherapp.Model.Weather;
import com.sefaunal.weatherapp.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    WeatherRepository weatherRepository;

    public void saveWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    public List<Weather> findWeatherByUser(User user){
        return weatherRepository.findByUser(user);
    }

    public void deleteByWeatherID(Long weatherID){
        weatherRepository.deleteById(weatherID);
    }

    public Weather findByWeatherID(Long weatherID){
        return weatherRepository.findById(weatherID).get();
    }

    public Page<Weather> findPage(User user, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);

        return weatherRepository.findAllByUser(user, pageable);
    }

    public Page<Weather> findWithSorting(String field, String direction, int pageNumber, User user){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(field).ascending() : Sort.by(field).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 7, sort);

        return weatherRepository.findAllByUser(user, pageable);
    }
}
