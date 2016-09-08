package a2013.android.asteroids2.util;


/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * LoggerConfig: **********************************************************************************
 * Use:
 * Log to the system log and then view in the LogCat;
 * can turn on / off as needed in development / production based on the boolean ON
 *
 * Summary:
 * loadTexture(Context context, int resourceId) :
 *      generate, decode into raw bm, bind, set minification/magnification filtering,
 *      load bitmap data into GLES, clean-up: release data, unbind
 *
 * For example:
 * To load a texture from R.drawable resource folder:
 * TextureHelper.loadTexture(context, R.drawable.texture)
 *
 **************************************************************************************************/


public class LoggerConfig {
    public static final boolean ON = true;
}
