package lib.gldefiningshapes;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLSurfaceView.*;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLDEFININGSHAPES: *******************************************************************************
 *
 **************************************************************************************************/


public class MyGLRenderer implements Renderer {

    private GLTriangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set  background color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        mTriangle = new GLTriangle();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // clear
        glClear(GL_COLOR_BUFFER_BIT);

        // invoke draw
        mTriangle.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = glCreateShader(type);

        // add the shader code and compile it
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        return shader;
    }


}