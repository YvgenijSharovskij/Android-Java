package lib.glrotation;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLROTATION: *************************************************************************************
 *
 **************************************************************************************************/

/*
* Notes
* *********************************************
* Basically, combine a transformation matrix with the projection view matrix to create rotation
* of the triangle at an angle based on the SystemClock.
*
*
*/





public class GLRotation extends AppCompatActivity {

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


