package com.studios.mordor.florist;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 13/03/2015.
 */
public class FloristContract {
    interface FloristColumns {
        //fields to database
        String FLORIST_NAME = "florist_name";
        String FLORIST_OCCASION = "florist_occasion";
        String FLORIST_TYPE = "florist_type";
        String FLORIST_COLORS = "florist_colors";
        String FLORIST_ORIGIN = "florist_origin";
        String FLORIST_MEANING = "florist_meaning";
        String FLORIST_AVAILABILITY = "florist_availability";
    }

    public static final String CONTENT_AUTHORITY = "com.studios.mordor.florist.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_FLORIST = "florist";
    public static final String[] TOP_LEVEL_PATHS = {
            PATH_FLORIST
    };

    public static class Florist implements FloristColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_FLORIST).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".florist";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".florist";

        public static Uri buildFloristUri(String flowerId) {
            return CONTENT_URI.buildUpon().appendEncodedPath(flowerId).build();
        }

        public static String getFlowerId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
