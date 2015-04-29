package com.lge.emojichat.util;

/**
 * Created by youngsik87.yoon on 2015-04-25.
 */
public class EmojiUtils {

    public static double getDistance(float startX, float startY, float endX, float endY) {
        final int powValue = 2;
        return Math.sqrt(Math.pow(Math.abs(endX - startX), powValue)
                + Math.pow(Math.abs(endY - startY), powValue));
    }

    public static float pointToAngle(float touchX, float touchY, float centerX, float centerY) {
        double radian = Math.atan2(touchY - centerY, touchX - centerX);
        if (radian < 0) {
            final int multipleValue = 2;
            radian = (Math.PI * multipleValue) + radian;
        }
        float degree = (float)(Math.toDegrees(radian) + 90);
        if (degree >= 360) {
            degree -= 360;
        }
        return degree;
    }

    public static void angleToPoint(float degree, float radius, float centerX, float centerY,
                                    float[] location) {
        final int xIndex = 0;
        final int yIndex = 1;
        location[xIndex] = (float)(centerX + Math.cos(Math.toRadians(270 + degree))
                * radius);
        location[yIndex] = (float)(centerY + Math.sin(Math.toRadians(270 + degree))
                * radius);
    }
}
