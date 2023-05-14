package com.shdwraze.discordbot.service;

import com.shdwraze.discordbot.model.CarDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CarService {

    CarDTO saveCar(CarDTO carDTO, HttpServletRequest request);

    void deleteCar(Integer carId);
}
