package com.example.poe.api.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class DiscordBotToken {
    @Value("${discord.bot.token}")
    private String discordBotToken;

    public String getDiscordBotToken() {
        return discordBotToken;
    }
}
