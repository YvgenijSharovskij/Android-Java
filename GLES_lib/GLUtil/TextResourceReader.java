package a2013.android.asteroids2.util;

import android.content.Context;
import android.content.res.Resources;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * TextResourceReader: *****************************************************************************
 * Use:
 * For reading of text files from the R.raw resources folder.
 *
 * For example:
 * to read the fragment_shader.glsl file and return the GLSL code as StringBuilder
 * with the readTextFileFromResource(Context context, int resourceId) method, pass the app context
 * and R.raw ID as args: readTextFileFromResource(this.context, R.raw.fragment_shader)
 *
 *
 **************************************************************************************************/

/**
 * For reading GLSL files:
 *
 * The way this will work is that we’ll call readTextFileFromResource() from our code,
 * and we’ll pass in the current Android context and the resource ID.
 *
 * For example, to read in  * the vertex shader, we might call the method as follows:
 * readTextFileFromResource(this.context, R.raw.fragment_shader)
 */

public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "IOException error: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resources.NotFoundException error: " + resourceId, nfe);
        }

        return body.toString();
    }
}
