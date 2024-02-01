package com.example.poe;


import com.example.poe.init.DiscordBotManager;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PoeApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(PoeApplication.class, args);
		DiscordBotManager discordBotManager = context.getBean(DiscordBotManager.class);
		JDA jda = discordBotManager.initializeJDA();
	}

}