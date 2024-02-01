package com.example.poe.listener;

import com.example.poe.command.CheckDiscordCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>디스 코드를 이용해서 메시지를 보낼 때 해당 메시지를 받고, 응답을 처리하는 Class</b>
 */
@Slf4j
public class DiscordListener extends ListenerAdapter {

    /**
     * <b>디스코드 사용자 메시지를 받게 되면 처리하게 되는 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
//        event.isFromType(ChannelType.PRIVATE)
        User user = event.getAuthor();
        Message message = event.getMessage();

        log.info("get message : " + message.getContentDisplay());

        if (user.isBot() || !message.getContentDisplay().startsWith("$")) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        EmbedBuilder embed = new EmbedBuilder();
        List<String> urlList;
        urlList = CheckDiscordCommand.checkBodyCommandForAPICall(message.getContentDisplay(),embed);
        sendMessage(event, embed, urlList);
        embed.clear();

    }

    private static byte[] toJson(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(data);
        } catch (IOException ex) {
            throw new RuntimeException("Error converting to JSON", ex);
        }
    }
    private static Map<String, Object> fromJson(byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructRawMapType(HashMap.class));
        } catch (IOException ex) {
            throw new ParsingException(ex);
        }
    }

    static class ParsingException extends RuntimeException {
        public ParsingException(Throwable cause) {
            super("Error parsing JSON", cause);
        }
    }

    private static Map<String,Object> init(int i){
        Map<String,Object> mapOfListOfMaps = new HashMap<>();
        Map<String, Object> option = new HashMap<>();
        List<Object> options = new ArrayList<>();
        option.put("label",2);
        option.put("value",2);
        options.add(option);
        mapOfListOfMaps.put("custom_id","row_" + i + "_select_0");
        mapOfListOfMaps.put("placeholder","11111111");
        mapOfListOfMaps.put("options",options);
        mapOfListOfMaps.put("min_values",1);
        mapOfListOfMaps.put("max_values",1);
        mapOfListOfMaps.put("type", Component.Type.STRING_SELECT.getKey());
        return mapOfListOfMaps;
    }

    private LayoutComponent getItemComponent(int i) {

        ItemComponent itemComponent = new ItemComponent() {
            @Override
            public Type getType() {
                return Type.STRING_SELECT;
            }

            @Override
            public DataObject toData() {
                Map<String,Object>  mapOfListOfMaps = init(i);
                byte[] jsonData = toJson(mapOfListOfMaps);
                return DataObject.fromJson(jsonData);
            }
        };

        LayoutComponent layoutComponent = new LayoutComponent() {
            @Override
            public DataObject toData() {
                System.out.println("toD1");
                Map<String, Object> map = new HashMap<>();
                Map<String,Object>  mapOfListOfMaps = init(i);
                List<Object> itemComponents = new ArrayList<>();
                itemComponents.add(mapOfListOfMaps);
                map.put("components",itemComponents);
                map.put("type", Type.ACTION_ROW.getKey());
                byte[] jsonData = toJson(map);

                return DataObject.fromJson(jsonData);
            }

            @Override
            public Type getType() {
                System.out.println("toD2");
                return Type.ACTION_ROW;
            }

            @Override
            public Iterator<ItemComponent> iterator() {
                System.out.println("toD3");
                return null;
            }

            @Override
            public List<ItemComponent> getComponents() {
                System.out.println("toD4");
                List<ItemComponent> itemComponentList = new ArrayList<>();
//                        itemComponentList.stream().collect(Collectors.groupingBy(Component::getType));
                itemComponentList.add(itemComponent);
                itemComponentList.add(itemComponent);
                return itemComponentList;
            }

            @Override
            public LayoutComponent withDisabled(boolean disabled) {
                System.out.println("toD5");
                return null;
            }

            @Override
            public LayoutComponent asDisabled() {
                System.out.println("toD6");
                return null;
            }

            @Override
            public LayoutComponent asEnabled() {
                return null;
            }

            @Override
            public LayoutComponent createCopy() {
                return null;
            }
        };
        return layoutComponent;
    }
    private void sendMessage(MessageReceivedEvent event, EmbedBuilder embed, List<String> urlList) {

        TextChannel textChannel = event.getChannel().asTextChannel();
        System.out.println(event);
        String returnMessage = event.getAuthor().getName() + "님 답변 드릴게요!" ;

        List<FileUpload> fileUploads = new ArrayList<>();
        if(urlList != null){
            try {
                for(String url : urlList) {
                    fileUploads.add(FileUpload.fromData(new File(url)));
                }
//                fileUploads.add(FileUpload.fromData(new File("https://cdn.poedb.tw/image/Art/2DItems/Gems/Support/Sacrifice.webp")));

                MessageEmbed.Thumbnail thumbnail = new MessageEmbed.Thumbnail("attach://Sacrifice.webp","",0,50);
                MessageEmbed.AuthorInfo authorInfo = new MessageEmbed.AuthorInfo("네이버어숴","https://mail.naver.com","https://web.poecdn.com/gen/image/WzI1LDE0LHsiZiI6IjJESXRlbXMvR2Vtcy9TdXBwb3J0L0NoYW5jZVRvQmxlZWQiLCJ3IjoxLCJoIjoxLCJzY2FsZSI6MX1d/51506e2da6/ChanceToBleed.png",null);
                MessageEmbed.Field field = new MessageEmbed.Field("필드이름","필드값",true);
                MessageEmbed embed1 = new MessageEmbed("https://www.naver.com","임베드제목","임베드 내용",
                        EmbedType.RICH, OffsetDateTime.now(),Color.BLUE.getRGB(), thumbnail, null ,authorInfo,null,null,null,List.of(field));


                textChannel.sendMessage("").addEmbeds(embed.build())
                        .addComponents(getItemComponent(1), getItemComponent(2))
                        .addEmbeds(embed1)
                        .addFiles(fileUploads).queue();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        } else {
            textChannel.sendMessage(returnMessage).setEmbeds(embed.build()).queue();
        }

//        event.getChannel().sendMessageEmbeds(embed.build()).queue();
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
