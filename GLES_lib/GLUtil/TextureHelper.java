package a2013.android.asteroids2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;


/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * TextureHelper: **********************************************************************************
 * Use:
 * Load a texture in OpenGL ES
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
 * Notes:
 * Uses LoggerConfig.ON boolean
 *
 **************************************************************************************************/


public class TextureHelper {

    private static final String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resourceId) {

        // generate the texture
        final int[] textureHandlerId = new int[1];  // int[1] array to pass to glGenTextures()
        glGenTextures(1, textureHandlerId, 0);
        // check for proper return value
        if (textureHandlerId[0] == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not generate a new texture (textureHandlerId[0] == 0).");
            }
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;       // original image data instead of default pre-scaling

        // decode into Android Bitmap resourceId with set BitmapFactory.Options()
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        // check for proper return value
        if (bitmap == null) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Resource ID " + resourceId + " could not be decoded (bitmap == null).");
            }
            // if the bitmap == null, no need to have the texture loaded, so delete it
            glDeleteTextures(1, textureHandlerId, 0);
            return 0;
        }

        // bind the texture as GL_TEXTURE_2D
        glBindTexture(GL_TEXTURE_2D, textureHandlerId[0]);

        // set min and mag filters (change as needed)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR); // MIN_FILTER: mipmap trilinear
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);               // MAG_FILTER: bilinear

        // load the texture
        /*
        * texImage2D(int target, int level, Bitmap bitmap, int border)
        *
        * @param target
        * @param level
        * @param bitmap
        * @param border
        *
        */
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        // clean-up: release data, unbind
        /**
         * Since Bitmaps reside in the native memory, it may take several cycles for the Dalvik VM
         * garbage collection; instead, recycle the Bitmap explicitly
         */
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureHandlerId[0];
    }
}
