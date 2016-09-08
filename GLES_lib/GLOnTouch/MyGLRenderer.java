package lib.glontouch;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLONTOUCH: **************************************************************************************
 *
 **************************************************************************************************/



public class MyGLRenderer implements GLSurfaceView.Renderer {

    private GLTriangle mTriangle;

    private final float[] mMVPMatrix = new float[16];           // Model View Projection Matrix
    private final float[] mProjectionMatrix = new float[16];    // projection matrix
    private final float[] mViewMatrix = new float[16];          // view matrix
    private float[] mRotationMatrix = new float[16];            // rotation matrix


    public volatile float mAngle;                               // rotation angle

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

        // rotate at mAngle: (the angle is set in the GLSurfaceView.onTouchEvent() method)
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);
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
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}




