let weather = {}

$('#searchInput').val("Turkey")
getWeatherFromAPI()

$("#SearchForm").submit(function (event){
    event.preventDefault();
    getWeatherFromAPI();
});

function FreeAPIAlert(){
    alert("You Can't view this information due to free API limitations")
}

function SaveWeather(){
    json = JSON.stringify(weather);

    $.ajax({
        url: "home/weather/save",
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (data){
            if (data) {
                alert("Weather Info has Been Successfully Saved.");
                document.getElementById("saveWeatherDIV").style.display='none';
            }
            else {
                alert("Saving Weather to the DB was failed.")
            }
        },

        error: function (data){
            console.log("ERROR", data);
        }
    })

}


function getWeatherFromAPI(){
    document.getElementById("DivDayName0").classList.add("active")
    document.getElementById("DivDayName1").classList.remove("active")
    document.getElementById("DivDayName2").classList.remove("active")

    document.getElementById("saveWeatherDIV").style.display='initial';

    $.ajax({
        url: "https://api.weatherapi.com/v1/forecast.json?key=86c191b77d0f47e78bb175729220807 &days=3 &q=" + $("#searchInput").val(),
        success: function (data, status, xhr){
            // console.log(data);

            $("#weatherCity").text(data.location.name + " / " + data.location.country)
            $("#weatherTime").text(data.current.last_updated + " (Last Updated)")
            $("#weatherTemp").text(parseFloat(data.current.temp_c) + " °C")
            $("#weatherStatus").text(data.current.condition.text)
            $("#weatherWind").text(data.current.wind_mph + "km/h")
            $("#weatherHumidity").text(data.current.humidity + "%")
            $("#weatherPressure").text(data.current.pressure_mb + " hPa")

            $("#weatherIcon").attr('src', data.current.condition.icon)

            days(data);
            document.getElementById("weatherWeek").style.display=""

            weather.weatherCity = data.location.name;
            weather.weatherCountry = data.location.country;
            weather.weatherStatus = data.current.condition.text;
            weather.weatherIconURL = data.current.condition.icon;
            weather.weatherTemp = parseFloat(data.current.temp_c);
            weather.weatherWind = data.current.wind_mph;
            weather.weatherHumidity = data.current.humidity;
            weather.weatherPressure = data.current.pressure_mb;
            weather.weatherDate = new Date(data.current.last_updated);

        },
        error: function (data, status, xhr){
            $("#weatherCity").text("?")
            $("#weatherTime").text("?")
            $("#weatherTemp").text("?")
            $("#weatherStatus").text("Couldn't found any city with that name...")
            $("#weatherWind").text("?")
            $("#weatherHumidity").text("?")
            $("#weatherPressure").text("?")

            $("#weatherIcon").attr('src', "")

            document.getElementById("weatherWeek").style.display="none"
            document.getElementById("saveWeatherDIV").style.display='none';
        }
    });
}


function getWeatherForecast(day){
    document.getElementById("saveWeatherDIV").style.display='none';

    $.ajax({
        url: "https://api.weatherapi.com/v1/forecast.json?key=86c191b77d0f47e78bb175729220807 &days=3 &q=" + $("#searchInput").val(),
        success: function (data, status, xhr){
            // console.log(data);

            $("#weatherCity").text(data.location.name + " / " + data.location.country)
            $("#weatherTime").text(data.forecast.forecastday[day].date)
            $("#weatherTemp").text(parseFloat(data.forecast.forecastday[day].day.avgtemp_c) + " °C")
            $("#weatherStatus").text(data.forecast.forecastday[day].day.condition.text)
            $("#weatherWind").text(data.forecast.forecastday[day].day.maxwind_mph + "km/h (Max)")
            $("#weatherHumidity").text(data.forecast.forecastday[day].day.avghumidity + "% (Average)")
            $("#weatherPressure").text(data.forecast.forecastday[day].hour[12].pressure_mb + " hPa (At 12AM)")
            $("#weatherIcon").attr('src', data.forecast.forecastday[day].day.condition.icon)

            switch (day){
                case 1:
                    document.getElementById("DivDayName1").classList.add("active")
                    document.getElementById("DivDayName0").classList.remove("active")
                    document.getElementById("DivDayName2").classList.remove("active")
                    break;
                case 2:
                    document.getElementById("DivDayName2").classList.add("active")
                    document.getElementById("DivDayName0").classList.remove("active")
                    document.getElementById("DivDayName1").classList.remove("active")
                    break;
            }
        }
    });
}


function days(data){

    for (let i=0; i<5; i++){
        let date = new Date();
        date.setDate(date.getDate() + i);
        let dayName = new Intl.DateTimeFormat('en', {weekday: 'short'}).format(date).toUpperCase();

        switch (i){
            case 0:
                $("#dayName0").text(dayName);
                $("#dayTemp0").text(parseFloat(data.current.temp_c) + " °C");
                break;
            case 1:
                $("#dayName1").text(dayName)
                $("#dayTemp1").text(parseFloat(data.forecast.forecastday[1].day.avgtemp_c) + " °C");
                break;
            case 2:
                $("#dayName2").text(dayName)
                $("#dayTemp2").text(parseFloat(data.forecast.forecastday[2].day.avgtemp_c) + " °C");
                break;
            case 3:
                $("#dayName3").text(dayName)
                break;
            case 4:
                $("#dayName4").text(dayName)
                break;
        }
    }
}

