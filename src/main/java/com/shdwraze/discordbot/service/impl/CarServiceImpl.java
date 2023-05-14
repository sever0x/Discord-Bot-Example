package com.shdwraze.discordbot.service.impl;

import com.shdwraze.discordbot.event.MessageSendEvent;
import com.shdwraze.discordbot.model.CarDTO;
import com.shdwraze.discordbot.model.entity.Car;
import com.shdwraze.discordbot.repository.CarRepository;
import com.shdwraze.discordbot.service.CarService;
import discord4j.rest.http.client.ClientException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final MessageSendEvent messageSendEvent;

    private final CarRepository carRepository;

    @Override
    public CarDTO saveCar(CarDTO carDTO, HttpServletRequest request) {
        try {
            messageSendEvent.sendMessage(
                    carRepository.save(Car.builder().brand(carDTO.brand()).model(carDTO.model()).build()),
                    request.getHeader("X-Forwarded-For")
            );
            return carDTO;

        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Moderator Bot exception");
        }
    }

    @Override
    public void deleteCar(Integer carId) {
        carRepository.deleteById(carId);
    }
}
