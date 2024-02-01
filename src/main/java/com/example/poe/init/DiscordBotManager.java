package com.example.poe.init;

import com.example.poe.listener.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBotManager {

    @Value("${discord.bot.token}")
    private String discordBotToken;

    public JDA initializeJDA() {
        return JDABuilder.createDefault(discordBotToken)
            .setActivity(Activity.playing("메시지 기다리는 중!"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(new DiscordListener())
            .build();

        // slash function
//		jda.updateCommands().addCommands(
//            Commands.slash("ping" ,"Calculate ping of the bot"),
//				Commands.slash("pang" ,"Calculate ping of the bot")
//                        ).queue();

        // config - lazy load
//		jda.awaitReady();
    }
}
