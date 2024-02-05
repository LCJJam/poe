package com.example.poe.listener;

import com.example.poe.command.CheckDiscordCommand;
import com.example.poe.common.dto.ReturnMsg;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.internal.requests.restaction.MessageCreateActionImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <b>디스 코드를 이용해서 메시지를 보낼 때 해당 메시지를 받고, 응답을 처리하는 Class</b>
 */
@Slf4j
public class DiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Message message = event.getMessage();

        log.info("get message : " + message.getContentDisplay());

        if (user.isBot() || !message.getContentDisplay().startsWith("$")) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        sendMessage(event, CheckDiscordCommand.checkBodyCommandForAPICall(message.getContentDisplay()));

    }

    private void sendMessage(MessageReceivedEvent event, List<ReturnMsg> returnMsgList) {

        TextChannel textChannel = event.getChannel().asTextChannel();
        String returnMessage = event.getAuthor().getName() + "님 "+returnMsgList.size()+"개의 답변 드릴게요!" ;
        textChannel.sendMessage(returnMessage).queue();

        MessageCreateActionImpl messageCreateAction = new MessageCreateActionImpl(event.getChannel().asTextChannel());
        List<MessageEmbed> messageEmbedList = new ArrayList<>();
        List<FileUpload> fileUploads = new ArrayList<>();
        Queue<ReturnMsg> queue = new LinkedList<>(returnMsgList);
        int count = 8;
        while(!queue.isEmpty()){
            count--;
            ReturnMsg returnMsg = queue.poll();
            if(returnMsg.getActionComponentList() != null) {
                messageCreateAction.addActionRow(returnMsg.getActionComponentList());

            } else if (returnMsg.getImgUrl() != null) {
                try {
                    File file = new File(returnMsg.getImgUrl());
                    messageEmbedList.add(returnMsg.getembed());
                    fileUploads.add(FileUpload.fromData(file));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    messageEmbedList.add(returnMsg.getembed());
                }
            } else {
                messageEmbedList.add(returnMsg.getembed());
            }
            if(count == 0 || queue.isEmpty()) {
                if(messageEmbedList.size() != 0) messageCreateAction.addEmbeds(messageEmbedList);
                if(fileUploads.size() != 0) messageCreateAction.addFiles(fileUploads);
                messageCreateAction.queue();
                messageCreateAction = new MessageCreateActionImpl(event.getChannel().asTextChannel());
                messageEmbedList = new ArrayList<>();
                fileUploads = new ArrayList<>();
                count = 8;
            }
        }
    }

    //    @Override
//    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//        switch(event.getName()) {
//            case("ping"):
//                event.reply("Pong!").setEphemeral(true)
//                        .flatMap(v ->
//                                event.getHook().editOriginalFormat("Pong: %d ms",System.currentTimeMillis())
//                        ).queue();
//            default:
//                System.out.printf("Unknown command %s used by %#s%n", event.getName(), event.getUser());
//        }
//
//    }
}
