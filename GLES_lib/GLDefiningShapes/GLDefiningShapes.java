package lib.gldefiningshapes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLDEFININGSHAPES: *******************************************************************************
 *
 **************************************************************************************************/

/*
* Notes
* *********************************************
* This is a simple example of manually drawing a triangle from a vertex array (x, y, z); passing
* the vertex data to the shader program, and drawing the triangle in the renderer.
*
* Note that shader loading can be done more efficiently, but is omitted here for the sake of
* simplicity.
*
*/



public class GLDefiningShapes extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance with the current app Context and
        // set it as the ContentView for this GLSurfaceViewActivity
        mGLSurfaceView = new MyGLSurfaceView(this);
        setContentView(mGLSurfaceView);
    }

    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            // set the GLES2.0 context
            setEGLContextClientVersion(2);

            // initiate the MyGLRenderer and
            // set it for drawing on the MyGLSurfaceView
            mRenderer = new MyGLRenderer();
            setRenderer(mRenderer);
        }
    }


}


