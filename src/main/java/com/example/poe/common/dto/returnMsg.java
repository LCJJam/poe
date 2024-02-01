package com.example.poe.common.dto;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class returnMsg {
    private String returnMsg;
    private String imgUrl;
    private List<EmbedBuilder> embedList;

    public returnMsg(String returnMsg, String imgUrl, List<EmbedBuilder> embedList){
        this.returnMsg = returnMsg;
        this.imgUrl = imgUrl;
        this.embedList = embedList;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<EmbedBuilder> getEmbedList() {
        return embedList;
    }

    public void setEmbedList(List<EmbedBuilder> embedList) {
        this.embedList = embedList;
    }
}
