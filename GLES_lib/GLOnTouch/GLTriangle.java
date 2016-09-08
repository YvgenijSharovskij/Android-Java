package lib.glontouch;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLONTOUCH: **************************************************************************************
 *
 **************************************************************************************************/




public class GLTriangle {

    private int mPositionHandle;
    private int mColorHandle;

    // for the view transformation: "Model View Projection Matrix"
    private int mMVPMatrixHandle;

    // for handling the vertex array
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public FloatBuffer vertexBuffer;

    // number of coordinates per vertex in the triangleCoords array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f, 0.622008459f, 0.0f,   // top
            -0.5f, -0.311004243f, 0.0f, // left
            0.5f, -0.311004243f, 0.0f   // right
    };

    // Set color as RBGA
    float color[] = { 0.63671875f, // R
            0.76953125f,           // B
            0.22265625f,           // G
            1.0f };                // opacity

    // GLSL shader code, passed directly as strings
    // GLSL shader code, passed directly as strings
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // shader program ID handle
    public final int mProgram;

    public GLTriangle() {
        // initialize vertex byte buffer for shape coordinates
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);

        // use native byte order
        bb.order(ByteOrder.nativeOrder());

        // pass buffer as float
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates
        vertexBuffer.put(triangleCoords);
        // set the buffer to read from position 0
        vertexBuffer.position(0);

        // load the shader codes from strings
        int vertexShader = MyGLRenderer.loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);

        // create the program
        mProgram = glCreateProgram();
        // attach the vertex shader
        glAttachShader(mProgram, vertexShader);
        // attach the fragment shader
        glAttachShader(mProgram, fragmentShader);

        // link the shaders into the program
        glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) { // pass in the calculated transformation matrix
        // use the linked program
        glUseProgram(mProgram);

        // handle to vertex shader's vPosition attribute
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        // enagle the handle
        glEnableVertexAttribArray(mPositionHandle);
        // Prepare the triangle coordinate data
        glVertexAttribPointer(mPositionHandle,
                COORDS_PER_VERTEX,
                GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer);

        // handle to fragment shader's vColor uniform
        mColorHandle = glGetUniformLocation(mProgram, "vColor");
        // set the vColor uniform variable
        glUniform4fv(mColorHandle, 1, color, 0);

        // apply projection 'n camera view:

        // set the "Model View Projection Matrix" handle to the "uMVPMatrix" uniform
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");

        // pass the projection and view transformation
        glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // draw
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        glDisableVertexAttribArray(mPositionHandle);
    }
}
