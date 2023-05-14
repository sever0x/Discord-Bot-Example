package com.shdwraze.discordbot.event;

import com.shdwraze.discordbot.model.entity.Car;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class MessageSendEvent {

    @Value("${discord.channel.id}")
    private final String discordChannelId;

    private final GatewayDiscordClient discordClient;

    public void sendMessage(Car car, String header) {
        Button approve = Button.success("approve", "Approve");
        Button reject = Button.danger("reject", "Reject");

        discordClient.getChannelById(Snowflake.of(discordChannelId))
                .ofType(TextChannel.class)
                .flatMap(channel -> channel.createMessage(createSpec(car, header))
                        .withComponents(
                                ActionRow.of(approve, reject)
                        )).subscribe();
    }

    private EmbedCreateSpec createSpec(Car car, String header) {
        return EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title(car.getBrand() + " " + car.getModel())
                .url("https://discord4j.com")
                .author("Some Name", "https://discord4j.com", "https://i.imgur.com/F9BhEoz.png")
                .description(String.format("ID: %s\nBrand: %s\nModel: %s\n\nIP: %s", car.getId(), car.getBrand(), car.getModel(), header))
                .thumbnail("https://i.imgur.com/F9BhEoz.png")
                .timestamp(Instant.now())
                .footer(String.valueOf(car.getId()), "https://i.imgur.com/F9BhEoz.png")
                .build();
    }
}
