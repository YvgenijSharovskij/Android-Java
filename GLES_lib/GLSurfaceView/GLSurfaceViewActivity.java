package lib.glsurfaceview;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * GLSURFACEVIEW: **********************************************************************************
 *
 **************************************************************************************************/

/*
* Notes
* *********************************************
* This is a basic view container for GLES graphics, based on an override of the standard
* android.opengl.GLSurfaceView class, and a GLSurfaceView.Renderer which controls what is drawn
* in the GLSurfaceView.
*
* Alternatives to this standard include partial GLES graphics rendering with TextView (which is
* supported since Android 4.0 Ice Cream Sandwich), and starting from scratch using SurfaceView.
*
*/

/*
* GLES use in the manifest
* *********************************************
* In order to use the OpenGL ES 2.0 API, declare it in the manifest as:
*
    <uses-feature android:glEsVersion="0x00020000" android:required="true" />
*
*/

/*
* GLES main activity
* *********************************************
* This is the most basic form of the main activity. Full Android cycle activity handling has been
* ignored for simplicity.
*
*/

/*
* GLES Renderer Class
* *********************************************
* The Renderer class controls graphics rendering from the GlSurfaceView by overriding these
* 3 methods:
*     1) onSurfaceCreated() - Called once to set up the view's GLES environment
*     2) onDrawFrame()      - Called for each redraw of the view
*     3) onSurfaceChanged() - Called when the geometry of the view changes, such as when the
*                             device's screen orientation changes
*
*/


public class GLSurfaceViewActivity extends AppCompatActivity {

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

        public MyGLSurfaceView(Context context){
            super(context);

            // set the GLES2.0 context
            setEGLContextClientVersion(2);

            // initiate the MyGLRenderer and
            // set it for drawing on the MyGLSurfaceView
            mRenderer = new MyGLRenderer();
            setRenderer(mRenderer);
        }
    }

    public class MyGLRenderer implements Renderer {

        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            // Set the background color with float values
            glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        }

        @Override
        public void onDrawFrame(GL10 unused) {
            // clear the screen
            glClear(GL_COLOR_BUFFER_BIT);
        }

        @Override
        public void onSurfaceChanged(GL10 unused, int width, int height) {
            // set the ViewPort
            glViewport(0, 0, width, height);
        }
    }
}
