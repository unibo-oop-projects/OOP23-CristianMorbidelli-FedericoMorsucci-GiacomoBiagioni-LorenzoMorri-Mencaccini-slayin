package slayin.model.utility;

public class Globals {
    public static GameResolution RESOLUTION = GameResolution.DEFAULT;

    // Score
    public static final int COMBO_RESET_TIME = 3000;

    public static int GRAVITY_CHARACTER=(int)(RESOLUTION.getHeight()/0.144);
    public static int FJUMP_CHARACTER=-(int)(RESOLUTION.getHeight()/0.36);
    public static int FLEFT_CHARACTER=-(int)(RESOLUTION.getWidth()/2.97);
    public static int FRIGHT_CHARACTER=(int)(RESOLUTION.getWidth()/2.97);

    //Shot
    public static int SPEEDX_BULLET_ROUND=(int)(RESOLUTION.getWidth()/1.42);
    public static int RADIUS_BULLET_ROUND=(int)(RESOLUTION.getWidth()/85.3 + RESOLUTION.getHeight()/48);
}
