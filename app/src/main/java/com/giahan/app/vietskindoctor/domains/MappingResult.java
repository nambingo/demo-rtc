package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by FRAMGIA\pham.duc.nam on 17/05/2018.
 */

public class MappingResult {
    @SerializedName("quests")
    List<Quests> mQuestsList;

    public List<Quests> getQuestsList() {
        return mQuestsList;
    }

    public void setQuestsList(List<Quests> questsList) {
        mQuestsList = questsList;
    }
}
