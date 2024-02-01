package com.example.poe.api.data;


public class SkillGem {
    private final String name;
    private final String tags;
    private final String eng;


    public SkillGem(String name, String tags, String eng){
        this.name = name;
        this.tags = tags;
        this.eng = eng;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getEng() {
        return eng;
    }


    public String toResult() {
        return this.name
                + "\n젬 태그 : " + this.tags
                + "\n젬 링크 : " + "https://poedb.tw/kr/" + this.eng;
    }
}
