package lib.glrotation;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLROTATION: *************************************************************************************
 *
 **************************************************************************************************/




public class MyGLRenderer implements GLSurfaceView.Renderer {

    private GLTriangle mTriangle;

    private final float[] mMVPMatrix = new float[16];           // Model View Projection Matrix
    private final float[] mProjectionMatrix = new float[16];    // projection matrix
    private final float[] mViewMatrix = new float[16];          // view matrix
    private float[] mRotationMatrix = new float[16];            // rotation matrix

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // set background color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        mTriangle = new GLTriangle();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // clear
        glClear(GL_COLOR_BUFFER_BIT);

        float[] scratch = new float[16];

        // rotation transformation of the triangle based on the SystemClock
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        // Set the View matrix
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // multiply the projection and view transformation matrices
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // multiply the rotation with the projection and camera view
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // draw
        mTriangle.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // set the ViewPort
        glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // use mProjectionMatrix as the view frustum to appear on the screen
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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