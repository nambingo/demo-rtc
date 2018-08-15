package com.giahan.app.vietskindoctor.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Message;
import com.giahan.app.vietskindoctor.domains.Session;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by pham.duc.nam on 23/05/2018.
 */

public class GeneralUtil {
    public static void setBackgroundColor(Context context, View view, int position) {
        if (position % 2 == 0) {
            view.setBackgroundColor(context.getResources().getColor(R.color.bg_item_gray));
        } else if (position % 2 == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    public static String toJSon(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T fromJSon(Class<T> clazz, String json) {
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<Message> sortMessage(List<Message> messageList) {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }

        Collections.sort(messageList, (o1, o2) -> Objects.requireNonNull(
                DateUtils.getDate(o1.getCreatedAt())).compareTo
                (DateUtils.getDate(o2.getCreatedAt())));
        return messageList;
    }

    public static List<Session> sortSession(List<Session> list){
        if (list == null) {
            list = new ArrayList<>();
        }

        Collections.sort(list, (o1, o2) -> Objects.requireNonNull(DateUtils.getDate(o2.getLastMessageAt()))
                .compareTo(DateUtils.getDate(o1.getLastMessageAt())));
        return list;
    }
}
