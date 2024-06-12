package slayin.model.utility;

public class Constants {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    // Score
    public static final int COMBO_RESET_TIME = 3000;

    public final static int GRAVITY_CHARACTER=(int)(WINDOW_HEIGHT/0.144);
    public final static int FJUMP_CHARACTER=-(int)(WINDOW_HEIGHT/0.36);
    public final static int FLEFT_CHARACTER=-(int)(WINDOW_WIDTH/2.97);
    public final static int FRIGHT_CHARACTER=(int)(WINDOW_WIDTH/2.97);

    //Shot
    public final static int SPEEDX_BULLET_ROUND=(int)(WINDOW_WIDTH/1.42);
    public final static int RADIUS_BULLET_ROUND=(int)(WINDOW_WIDTH/85.3 + WINDOW_HEIGHT/48);
}
