package com.example.poe.command;

import com.example.poe.common.dto.ReturnMsg;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.example.poe.command.Commands.*;

/**
 * <b>디스코드를 통해 사용자가 보낸 메시지를 분석하여 처리하기 위한 객체</b>
 */
@Slf4j
public class CheckDiscordCommand {

    /**
     * <b>API 호출을 통해 결과값을 받으려는 명령어가 들어왔을 때를 처리하기 위한 Method</b>
     *
     * @param message 사용자가 보낸 메시지
     * @return Image Embed 를 위한 경로
     */

    public static List<ReturnMsg> checkBodyCommandForAPICall(String message) {
        String[] messageArray = message.split("!");
        if (messageArray.length == 2) {
            switch (messageArray[0]) {
                case "$젬":
                    return gems(messageArray);
                case "$직업노드":
                    return classes(messageArray);
//                case "$전직노드":
//                    return upgradeClasses(messageArray);
                case "$스킬","$보조":
                    return gemTags(messageArray);
                default:
                    return errorCommand();
            }
        }

        if (messageArray.length == 1) {
            switch (messageArray[0]) {
                case "$site", "$사이트":
                    return getSite();
                default:
                    return errorCommand();
            }
        } else {
            return errorCommand();
        }
    }
    public static List<ReturnMsg> errorCommand(){
        List<ReturnMsg> returnMsgList = new ArrayList<>();
        ReturnMsg returnMsg = new ReturnMsg();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("명령어를 확인해 주세요" +
                "\n\n- 젬 태그 검색" +
                "\n${'스킬' or '보조'}!{젬태그 젬태그 젬태그}" +
                "\n\n- 젬 이름 검색" +
                "\n$젬!{젬이름}" +
                "\n\n- 직업 종류" +
                "\n$직업노드!{캐릭터}" +
                "\n\n- 전직 노드" +
                "\n$전직노드!{전직}" +
                "\n\n- 사이트 모음" +
                "\n$site" +
                "\n\n~~$link!{category}~~"
        );
        embedBuilder.setColor(Color.RED);
        returnMsg.setembed(embedBuilder.build());
        returnMsgList.add(returnMsg);
        embedBuilder.clear();
        return returnMsgList;
    }
}
