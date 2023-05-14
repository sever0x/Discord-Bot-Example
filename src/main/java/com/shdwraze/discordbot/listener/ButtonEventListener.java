package com.shdwraze.discordbot.listener;

import com.shdwraze.discordbot.service.CarService;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageEditSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Service
public class ButtonEventListener implements EventListener<ButtonInteractionEvent> {

    private final CarService carService;

    @Autowired
    public ButtonEventListener(@Lazy CarService carService) {
        this.carService = carService;
    }

    @Override
    public Class<ButtonInteractionEvent> getEventType() {
        return ButtonInteractionEvent.class;
    }

    @Override
    public Mono<Void> execute(ButtonInteractionEvent event) {
        Integer carId = Integer.valueOf(event.getMessage().get().getEmbeds().get(0).getFooter().get().getText());
        log.error(String.valueOf(carId));

        if ("approve".equals(event.getCustomId())) {
            Mono<Message> edit = event.editReply()
                    .withContentOrNull("This message has been approved")
                    .flatMap(message -> message.edit(MessageEditSpec.builder()
                            .addAllComponents(Collections.emptyList()).build()));
            return event.deferEdit().then(edit).then();

        } else if ("reject".equals(event.getCustomId())) {
            Mono<Message> edit = event.editReply()
                    .withContentOrNull("This message has been rejected")
                    .flatMap(message -> message.edit(MessageEditSpec.builder()
                            .addAllComponents(Collections.emptyList()).build()));
            carService.deleteCar(carId);
            return event.deferEdit().then(edit).then();

        }
        return Mono.empty();
    }
}
