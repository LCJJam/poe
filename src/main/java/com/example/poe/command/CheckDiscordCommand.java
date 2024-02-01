package com.example.poe.command;

import com.example.poe.api.common.config.SkilGemInit;
import com.example.poe.api.common.config.SupportGemInit;
import com.example.poe.api.data.SkillGem;
import com.example.poe.api.data.SupportGem;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.internal.entities.InviteImpl;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    public static List<String> checkBodyCommandForAPICall(String message, EmbedBuilder embedBuilder) {

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setFooter("ⓒ 2024. All Rights Reserved. \n Bot Version : 1.0.0b\n");
        String[] messageArray = message.split("!");
        if (messageArray.length == 2) {
            switch (messageArray[0]) {
                case "$젬":
                    return gems(messageArray, embedBuilder);
                case "$직업노드":
                    return classes(messageArray, embedBuilder);
                case "$전직노드":
                    return upgradeClasses(messageArray, embedBuilder);
                case "$link", "$링크":
                    return getLink(messageArray, embedBuilder);
                case "$스킬","$보조":
                    return gemTags(messageArray, embedBuilder);
                default:
                    errorCommand(embedBuilder);
                    return null;
            }
        }

        if (messageArray.length == 1) {
            switch (messageArray[0]) {
                case "$site", "$사이트":
                    return getSite(messageArray, embedBuilder);
                default:
                    errorCommand(embedBuilder);
                    return null;
            }
        }
        errorCommand(embedBuilder);
        return null;
    }
    public static List<String> getSite(String[] messageArray, EmbedBuilder embedBuilder){
        embedBuilder.setDescription("" +
                "- POE 페이지" +
                "\n  - https://poe.game.daum.net" +
                "\n- POEDB 디비" +
                "\n  - https://poedb.tw/kr" +
                "\n- poe.ninja 닌자" +
                "\n  - https://poe.ninja" +
                "\n- POE LAB 랩" +
                "\n  - https://www.poelab.com" +
                "\n- CRAFT of Exile 시뮬" +
                "\n  - https://www.craftofexile.com" +
                "\n- Trade of Exile 거래소" +
                "\n  - https://poe.game.daum.net/trade"
        );
        return null;
    }
    public static List<String> getLink(String[] messageArray, EmbedBuilder embedBuilder){
        switch (messageArray[1].toLowerCase()){
            case "db","디비":
                embedBuilder.setDescription("- POEDB" +
                        "\nhttps://poedb.tw/kr");
                return null;
            case "ninja","닌자":
                embedBuilder.setDescription("- poe.ninja" +
                        "\nhttps://poe.ninja");
                return null;
            case "lab","랩","렙","미궁":
                embedBuilder.setDescription("- POE LAB" +
                        "\nhttps://www.poelab.com");
                return null;
            case "craft","크래프트","크레프트","시뮬","시뮬레이션":
            embedBuilder.setDescription("- CRAFT of Exile" +
                    "\nhttps://www.craftofexile.com");
            return null;
            default:
                embedBuilder.setDescription("Category 리스트를 확인해주세요" +
                        "\ndb, 디비 : PoEDB" +
                        "\nninja, 닌자 : poe.ninja" +
                        "\nlab, 랩, 미궁" +
                        "\ncraft, 크래프트, 크레프트, 시뮬, 시뮬레이션"
                );
                embedBuilder.setColor(Color.RED);
                return null;
        }
    }

    public static void errorCommand(EmbedBuilder embedBuilder){
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
    }

    public static List<String> classes(String[] messageArray, EmbedBuilder embedBuilder){
        String result;
        switch (messageArray[1]){
            case "듀얼리스트":
                result = "- 슬레이어"
                        + "\n- 글래디에이터"
                        + "\n- 챔피언";
                break;
            case "쉐도우":
                result = "- 어쌔신"
                        + "\n- 사보추어"
                        + "\n- 트릭스터";
                break;
            case "머라우더":
                result = "- 저거넛"
                        + "\n- 버서커"
                        + "\n- 치프틴";
                break;
            case "위치":
                result = "- 네크로맨서"
                        + "\n- 오컬티스트"
                        + "\n- 엘리멘탈리스트";
                break;
            case "레인저":
                result = "- 데드아이"
                        + "\n- 레이더"
                        + "\n- 패스파인더";
                break;
            case "템플러":
                result = "- 인퀴지터"
                        + "\n- 하이로펀트"
                        + "\n- 가디언";
                break;
            case "사이온":
                result = "- 사이온";
                break;
            default:
                embedBuilder.setColor(Color.RED);
                result = "캐릭터 리스트를 확인해 주세요."
                        + "\n- 듀얼리스트"
                        + "\n- 쉐도우"
                        + "\n- 머라우더"
                        + "\n- 위치"
                        + "\n- 레인저"
                        + "\n- 템플러"
                        + "\n- 사이온"
                ;
                break;
        }
        embedBuilder.setDescription(result);

        return null;
    }

    public static List<String> upgradeClasses(String[] messageArray, EmbedBuilder embedBuilder){
        String result;
        switch (messageArray[1]){
            case "슬레이어":
                result = "- 공격 피해 10% 증가, 공격 속도 5% 증가"
                        + "\n  - 최근 4초 이내 적을 처치한 경우 피해 10% 증폭, 반사된 물리 피해를 받지 않음, 고유 적에 대한 적중 및 상태 이상 피해 20% 증폭"
                        + "\n    - 공격 피해 10% 증가, 공격 속도 5% 증가"
                        + "\n      - 플레이어의 스킬로 생명력이 20% 이하인 적 처치, 희귀 또는 고유 적 처치 시 20초 동안 공격 속도 10% 증가, 적 처치 시 20초 동안 이동 속도 10% 증가"
                        + "\n- 공격 피해 10% 증가, 치명타 확률 15% 증가"
                        + "\n  - 무기 공격의 기본 치명타 확률 8%, 주변의 적 하나당 치명타 피해 배율 +10%, 최대 +100%"
                        + "\n- 공격 피해 10% 증가, 효과 범위 10% 증가"
                        + "\n  - 일반 정확도 50% 증가, 근접 타격 범위 +0.4미터, 최근 4초 이내 처치한 적 하나당 효과 범위 5% 증가, 최대 50%, 적이 가까울수록 주는 근접 피해가 최대 15% 증폭"
                        + "\n- 공격 피해 10% 증가, 인내 충전 지속시간 15% 증가, 격분 충전 지속시간 15% 증가"
                        + "\n  - 인내 충전 최대치가 격분 충전 최대치와 동일, 격분 충전 최대치 +1"
                        + "\n- 공격 피해 10% 증가, 공격 피해의 0.4%를 생명력으로 흡수"
                        + "\n  - 생명력 흡수당 회복량 최대치 100% 증가, 점유되지 않은 생명력이 찼을 때 생명력 흡수 효과가 제거되지 않음, 흡수하는 동안 받는 피해 10% 감소"
                        + "\n    - 공격 피해 10% 증가, 공격 피해의 0.4%를 생명력으로 흡수"
                        + "\n      - 과잉살상 피해의 20%를 생명력으로 흡수, 흡수하는 동안 공격 속도 20% 증가, 흡수하는 동안 기절할 수 없음, 흡수의 영향을 받는 동안 출혈의 영향을 받지 않음"
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Slayer"
                ;
                break;
            case "글래디에이터":
                result = "- 공격 속도 4% 증가, 공격 피해 막기 확률 +2%"
                        + "\n  - 공격 속도 4% 증가, 공격 피해 막기 확률 +2%"
                        + "\n  - 공격 피해 막기 확률 +10%, 피격을 막을 경우 기절할 수 없음, 반격으로 2배의 피해를 줌"
                        + "\n    - 공격 속도 4% 증가, 공격 피해 막기 확률 +2%"
                        + "\n      - 공격 피해 막기 확률 최대치 +10%, 공격 피해 막기 확률 1%당 방어도 및 회피 +10"
                        + "\n    - 공격 속도 4% 증가, 공격 피해 막기 확률 +2%"
                        + "\n      - 지난 20초 이내에 막아낸 경우 공격 피해에 행운 적용, 지난 20초 이내에 막아낸 경우 명중 시 적 몬스터 물리 피해 감소를 무시"
                        + "\n- 공격 속도 4% 증가, 한손 무기 피해 15% 증가"
                        + "\n  - 주 무기로 처치 시 25%의 확률로 격분 충전 획득, 보조 장비로 처치 시 25%의 확률로 인내 충전 획득, 최대 격분 충전 상태에서 물리 피해 10% 증폭, 최대 인내 충전 상태에서 받는 물리 피해 10% 감소"
                        + "\n- 공격 속도 4% 증가, 한손 무기 피해 15% 증가"
                        + "\n  - 도전자 충전 하나당 공격 및 이동 속도 2% 증폭, 핏빛 태세에서 희귀 또는 고유 적 명중 시 25%의 확률로 도전자 충전 획득, 모래 태세에서 적 처치 시 도전자 충전 획득, 최대 도전자 충전 +10"
                        + "\n- 공격 속도 4% 증가, 물리 피해 10% 증가"
                        + "\n  - 공격 시 50%의 확률로 출혈 유발, 출혈 중인 적 명중 시 힘줄 절단 유발, 출혈 중인 적 명중 시 25%의 확률로 적 실명 유발, 힘줄 절단시킨 적이 받는 물리 피해 10% 증가"
                        + "\n    - 공격 속도 4% 증가, 물리 피해 10% 증가"
                        + "\n      - 출혈 중인 적을 처치하면 적이 폭발하여 최대 생명력의 10%를 물리 피해로 줌, 지속 물리 피해 20% 증폭"
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Gladiator"
                ;
                break;
            case "챔피언":
                result = "- 회피 및 방어도 15% 증가, 방어 상승 상태에서 공격 스킬의 피해 10% 증가"
                        + "\n  - 방어 상승 상태에서 공격 속도 20% 증가, 방어 상승 상태에서 방어도 및 회피 +500, 방어 상승 상태에서 기절할 수 없음"
                        + "\n    - 회피 및 방어도 15% 증가, 방어 상승 상태에서 공격 스킬의 피해 10% 증가"
                        + "\n      - 방어 상승 20"
                        + "\n- 회피 및 방어도 15% 증가, 회피 및 방어도 15% 증가"
                        + "\n    - 생명력이 최대인 적 명중 시 영구적으로 적 위협, 낮은 생명력 상태가 되면20초 동안 아드레날린 획득, 아드레날린 획득 시 생명력의 25% 회복, 아드레날린 획득 시 모든 상태 이상 및 화상 제거"
                        + "\n- 회피 및 방어도 15% 증가, 공격 명중 시 10%의 확률로 적 꿰뚫음"
                        + "\n  - 플레이어가 부여한 꿰뚫기 효과가 1회 추가로 적중할 동안 유지됨, 최근 4초 이내 적을 꿰뚫은 경우, 자신 및 주변 동료들의 방어도 +1000, 자신 및 주변 동료들이 적을 꿰뚫을 때마다 물리 피해 6~12 추가"
                        + "\n- 회피 및 방어도 15% 증가, 도발 지속시간 20% 증가"
                        + "\n  - 명중 시 100%의 확률로 도발, 최근 4초 이내 적을 도발한 경우 받는 피해 10% 감소, 플레이어가 도발한 적이 다른 대상에게 주는 적중 및 상태 이상 피해 20% 감폭"
                        + "\n    - 회피 및 방어도 15% 증가, 도발 지속시간 20% 증가"
                        + "\n      - 플레이어가 도발한 적이 받는 피해 20% 증가, 플레이어가 도발한 적은 공격 회피 불가"
                        + "\n- 회피 및 방어도 15% 증가, 자신의 비-저주 오라 스킬 효과 5% 증가"
                        + "\n  - 자신의 비-저주 오라 스킬 효과 30% 증가, 설치한 깃발의 영향을 받는 플레이어 및 동료들이 각 단계에서 1초마다 생명력의 0.1% 재생, 깃발 스킬로 인한 점유 없음, 깃발 생성 시 설치한 깃발 단계의 40% 획득"
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Champion"
                ;
                break;
            case "어쌔신":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Champion"
                ;
                break;
            case "사보추어":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Saboteur"
                ;
                break;
            case "트릭스터":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Trickster"
                ;
                break;
            case "저거넛":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Juggernaut"
                ;
                break;
            case "버서커":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Berserker"
                ;
                break;
            case "치프틴":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Chieftain"
                ;
                break;
            case "네크로맨서":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Necromancer"
                ;
                break;
            case "오컬티스트":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Occultist"
                ;
                break;
            case "엘리멘탈리스트":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Elementalist"
                ;
                break;
            case "데드아이":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Deadeye"
                ;
                break;
            case "레이더":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Raider"
                ;
                break;
            case "패스파인더":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Pathfinder"
                ;
                break;
            case "인퀴지터":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Inquisitor"
                ;
                break;
            case "하이로펀트":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Hierophant"
                ;
                break;
            case "가디언":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Guardian"
                ;
                break;
            case "어센던트":
                result = "- "
                        + "\nlink : https://poe.game.daum.net/ascendancy/classes/Ascendant"
                ;
                break;
            default:
                embedBuilder.setColor(Color.RED);
                result = "캐릭터 리스트를 확인해 주세요."
                        + "\n- 듀얼리스트 : 슬레이어 / 글래디에이터 / 챔피언"
                        + "\n- 쉐도우 : 어쌔신 / 사보추어 / 트릭스터"
                        + "\n- 머라우더 : 저거넛 / 버서커 / 치프틴"
                        + "\n- 위치 : 네크로맨서 / 오컬티스트 / 엘리멘탈리스트"
                        + "\n- 레인저 : 데드아이 / 레이더 / 패스파인더"
                        + "\n- 템플러 : 인퀴지터 / 하이로펀트 / 가디언"
                        + "\n- 사이온 : 어센던트"
                ;
                break;
        }
        embedBuilder.setDescription(result);

        return null;
    }
    public static List<String> gemTags(String[] messageArray, EmbedBuilder embedBuilder){
        
        String[] tagsList = messageArray[1].split(" ");
        List<String> urlList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        switch (messageArray[0]) {
            case("$스킬") :
                SkilGemInit skilGemInit = new SkilGemInit();
                List<SkillGem> skill_gemsList = new ArrayList<>();
                for(SkillGem sk : skilGemInit.getDataList().values()) {
                    int flag = tagsList.length;
                    for(String tag : tagsList) {
                        if(!sk.getTags().contains(tag))
                            --flag;
                    }
                    if(flag == tagsList.length) skill_gemsList.add(sk);
                }
                for(SkillGem sk : skill_gemsList){
                    result.append(sk.toResult()).append("\n\n");
                    urlList.add("C:\\workspace\\poe\\src\\main\\resources\\image\\Gems\\"+sk.getEng()+".webp");
                    embedBuilder.setThumbnail("attachment://"+ sk.getEng() +".webp");
                }
                embedBuilder.setDescription(result);

                return urlList;
            case("$보조") :
                SupportGemInit supportGemInit = new SupportGemInit();
                List<SupportGem> supportGemList = new ArrayList<>();
                for(SupportGem su : supportGemInit.getDataList().values()) {
                    int flag = tagsList.length;
                    for(String tag : tagsList) {
                        if(!su.getTags().contains(tag))
                            --flag;
                    }
                    if(flag == tagsList.length) supportGemList.add(su);
                }

                for(SupportGem su : supportGemList){
                    result.append(su.toResult()).append("\n\n");
                    urlList.add("C:\\workspace\\poe\\src\\main\\resources\\image\\Gems\\"+su.getEng()+".webp");
                    embedBuilder.setThumbnail("attachment://"+ su.getEng() +".webp");
                }
                embedBuilder.setDescription(result);

                return urlList;

            default:
                return null;

        }
    }
    
    public static List<String> gems(String[] messageArray, EmbedBuilder embedBuilder){
        String str = messageArray[1].replaceAll(" ","");
        String result;
        SkilGemInit skilGemInit = new SkilGemInit();
        SkillGem skill_gem = skilGemInit.getDataList().get(str);

        SupportGemInit supportGemInit = new SupportGemInit();
        SupportGem supportGem = supportGemInit.getDataList().get(str);

        if(skill_gem == null && supportGem == null) {
            embedBuilder.setDescription("젬 리스트를 확인해 주세요.");
            embedBuilder.setColor(Color.RED);
            return null;
        } else if(supportGem == null) {
            String eng = skill_gem.getEng();
            result = skill_gem.toResult();
//            embedBuilder.setThumbnail("attachment://"+ eng +".webp");
            embedBuilder.setThumbnail("https://cdn.poedb.tw/image/Art/2DItems/Gems/Support/ElementalBurstSupportGem.webp");
            embedBuilder.setImage("attachment://"+ "Cleave" +".webp");
            embedBuilder.setDescription(result);
            return List.of("C:\\workspace\\poe\\src\\main\\resources\\image\\Gems\\" + eng + ".webp","C:\\workspace\\poe\\src\\main\\resources\\image\\Gems\\" + "Cleave" + ".webp");
        } else {
            String eng = supportGem.getEng();
            result = supportGem.toResult();

//            embedBuilder.setThumbnail("attachment://"+ eng +".webp");
            embedBuilder.setThumbnail("https://cdn.poedb.tw/image/Art/2DItems/Gems/Support/ElementalBurstSupportGem.webp");
            embedBuilder.setDescription(result);
            return Collections.singletonList("img path");
        }


    }
}
