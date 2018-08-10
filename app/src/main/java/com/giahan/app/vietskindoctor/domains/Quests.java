package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by pham.duc.nam
 */

public class Quests {
    private int id;
    private String title;
    @SerializedName("answers")
    private List<Answers> mAnswersList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Answers> getAnswersList() {
        return mAnswersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        mAnswersList = answersList;
    }
}
