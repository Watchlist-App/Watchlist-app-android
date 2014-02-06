package com.watchlistapp.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class DisplayUtil {
    public static String getPosterSize(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();
        String posterSize = null;
        if((displayWidth == 480 && displayHeight == 800) || (displayWidth == 540 && displayHeight == 960)) {
            posterSize = PosterSizesUtil.BIG;
        } else {
            posterSize = PosterSizesUtil.DOUBLE_BIG;
        }
        return posterSize;
    }

    public static String getActorAvatarSize(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();
        String posterSize = null;
        if((displayWidth == 480 && displayHeight == 800) || (displayWidth == 540 && displayHeight == 960)) {
            posterSize = PosterSizesUtil.SMALL;
        } else {
            posterSize = PosterSizesUtil.MEDIUM;
        }
        return posterSize;
    }

    public static String getCrewAvatarSize(Context context) {
        return getActorAvatarSize(context);
    }
}
