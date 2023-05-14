package com.shdwraze.discordbot.controller;

import com.shdwraze.discordbot.model.CarDTO;
import com.shdwraze.discordbot.service.CarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {

    private CarService carService;

    @PostMapping("/car")
    public CarDTO saveCar(@RequestBody CarDTO carDTO, HttpServletRequest request) {
        return carService.saveCar(carDTO, request);
    }
}
