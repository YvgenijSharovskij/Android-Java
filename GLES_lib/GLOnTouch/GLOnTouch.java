package lib.glontouch;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLONTOUCH: **************************************************************************************
 *
 **************************************************************************************************/

/*
* Notes
* *********************************************
* Basically, override the GLSurfaceView.onTouchEvent() method and use MotionEvent.ACTION_MOVE
* to set the angle based on the move event coordinates.
*
*
*
*/





public class GLOnTouch extends AppCompatActivity {

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

        // for the onTouchEvent()
        private final float TOUCH_FACTOR = 180.0f / 320; // proportional to 180 degrees
        private float mPrevX;
        private float mPrevY;

        public MyGLSurfaceView(Context context) {
            super(context);

            // set the GLES2.0 context
            setEGLContextClientVersion(2);

            // initiate the MyGLRenderer and
            // set it for drawing on the MyGLSurfaceView
            mRenderer = new MyGLRenderer();
            setRenderer(mRenderer);

            // redraw only when the view has changed:
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            // MotionEvent controls input motions;
            // get x and y touch coordinates:
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPrevX;
                    float dy = y - mPrevY;

                    // reverse direction at y half-screen
                    if (y > getHeight() / 2) {
                        dx = dx * -1 ;
                    }

                    // reverse direction at x half-screen
                    if (x < getWidth() / 2) {
                        dy = dy * -1 ;
                    }

                    // set the angle of the triangle proportional to the move event
                    mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * TOUCH_FACTOR));
                    requestRender();
            }

            mPrevX = x;
            mPrevY = y;
            return true;
        }
    }


}


