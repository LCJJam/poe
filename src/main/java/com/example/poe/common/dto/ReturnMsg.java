package com.example.poe.common.dto;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionComponent;

import java.util.List;

public class ReturnMsg {
    private String imgUrl;
    private MessageEmbed embed;
    private List<ActionComponent> actionComponentList;

    public ReturnMsg(String imgUrl, MessageEmbed embed, List<ActionComponent> actionComponentList){
        this.imgUrl = imgUrl;
        this.embed = embed;
        this.actionComponentList = actionComponentList;
    }

    public ReturnMsg() {

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public MessageEmbed getembed() {
        return embed;
    }

    public void setembed(MessageEmbed embed) {
        this.embed = embed;
    }

    public List<ActionComponent> getActionComponentList() {
        return actionComponentList;
    }

    public void setActionComponentList(List<ActionComponent> actionComponentList) {
        this.actionComponentList = actionComponentList;
    }
}
