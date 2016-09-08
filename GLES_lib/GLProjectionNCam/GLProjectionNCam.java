package lib.glprojectionncam;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLPROJECTIONNCAM: *******************************************************************************
 *
 **************************************************************************************************/

/*
* Notes
* *********************************************
* The height and width of the GLSurfaceView is used in projection transformation matrix which is
* passed as the view frustrum with the Matrix.frustumM() method, and then combined with a
* camera view transformation in the onDrawFrame() method.
*
*
*/






public class GLProjectionNCam extends AppCompatActivity {

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


