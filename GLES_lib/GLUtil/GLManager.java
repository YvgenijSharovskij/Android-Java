package a2013.android.asteroids2.util;

import android.content.Context;
import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/***************************************************************************************************
 * Android Java:
 * OpenGL ES:
 * library of recyclable classes:
 *
 *
 * GLManager: **********************************************************************************
 * Use:
 * base class for building shader programs, loaded with TextResourceReader (from the same Util
 * package)
 *
 * Summary:
 * base shader manager/helper:
 *  steps:
 *  1) create, upload and compile the shader: single method for each shader:
 *  1.1) glCreateShader(type); -- The type can be GL_VERTEX_SHADER for a vertex
 *                              shader, or GL_FRAGMENT_SHADER for a fragment shader.
 *  1.2) glShaderSource(shaderObjectId, shaderCode); -- upload the source code.
 *
 *  1.3) glCompileShader(shaderObjectId); -- compile
 *
 *  2) check compilation: check, get verbose log, delete if 0:
 *  2.1) glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
 *     -- check if OpenGL was able to successfully  compile the shader;
 *     note: this is a common pattern in GLES on Android. Pass an int array with the length of 1
 *     to a GLES class, and store the result in the array's 1st element.
 *
 *  2.2) glGetShaderInfoLog(shaderObjectId) -- human-readable message in addition to 0/1
 *
 *  2.3) glDeleteShader(shaderObjectId) if compileStatus == 0
 *
 *  3.0) link vertex and fragment shaders into a program:
 *
 *  3.1) glCreateProgram()
 *
 *  3.2) glAttachShader(programObjectId, vertexShaderId);
 *       glAttachShader(programObjectId, fragmentShaderId);
 *
 *  3.3)  glLinkProgram(programObjectId);
 *
 *  4.1-3) check link status: check, get verbose log, delete if 0
 *
 *  5.1-3) validate program -- for development purposes
 *
 *
 * For example:
 * To build a program with the fragment and vertex shaders loaded with
 * TextResourceReader.readTextFileFromResource()
 * (can be invoked by declaring a new instance of the GLManager constructor):
 *         program = GLManager.buildProgram(
 *                           TextResourceReader.readTextFileFromResource(
 *                               context, vertexShaderResourceId),
 *                           TextResourceReader.readTextFileFromResource(
 *                               context, fragmentShaderResourceId));
 *
 *
 * Notes:
 * uses other GLUtil classes: TextResourceReader, LoggerConfig
 *
 **************************************************************************************************/





public class GLManager {

    private static final String TAG = "GLManager";

    // constants to help to get through the array
    public static final int COMPONENTS_PER_VERTEX = 3;
    public static final int FLOAT_SIZE = 4;
    public static final int STRIDE = (COMPONENTS_PER_VERTEX)* FLOAT_SIZE;
    public static final int ELEMENTS_PER_VERTEX = 3;// x,y,z
    public static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;

    // constants for uniform and attribute GLSL variables in the shaders
    public static final String U_MATRIX = "u_Matrix";
    public static final String A_POSITION = "a_Position";
    public static final String U_COLOR = "u_Color";

    // matching ints for the above constants
    // to store their locations
    public static int uMatrixLocation;
    public static int aPositionLocation;
    public static int uColorLocation;

    public static int program;

    public GLManager(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {

            // compile and link the program
            // (done only once in the constructor, since it is computationally heavy)
            program = buildProgram(
                    TextResourceReader.readTextFileFromResource(
                            context, vertexShaderResourceId),
                    TextResourceReader.readTextFileFromResource(
                            context, fragmentShaderResourceId));
    }

    public static int getGLProgram(){
        return program;
    }

    public static int buildProgram(String vertexShaderCode, String fragmentShaderCode){
        // compile the shaders
        int mVertexShader = compileVertexShader(vertexShaderCode);
        int mFragmentShader = compileFragmentShader(fragmentShaderCode);

        // link the shaders
        program = linkProgram(mVertexShader, mFragmentShader);

        if (LoggerConfig.ON) {
            validateProgram(program);
        }

        return program;

    }

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        // -----------------------------------------------------------------------------------------
        // 1) create, upload and compile the shader
        // -----------------------------------------------------------------------------------------
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);

        glCompileShader(shaderObjectId);

        // -----------------------------------------------------------------------------------------
        // 2) check compilation status: check, get verbose log, delete if 0
        // -----------------------------------------------------------------------------------------
        final int[] compileStatus = new int[1];     // make 1 int array, and retrieve by starting glGetShaderiv() at 0
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        // get verbose log
        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                    + glGetShaderInfoLog(shaderObjectId));
        }

        // delete shader if 0
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        // -----------------------------------------------------------------------------------------
        // 3) link vertex and fragment shaders into a program: create, attach, link
        // -----------------------------------------------------------------------------------------
        program = glCreateProgram();
        if (program == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }

        // attach
        glAttachShader(program, vertexShaderId);
        glAttachShader(program, fragmentShaderId);

        // link
        glLinkProgram(program);

        // -----------------------------------------------------------------------------------------
        // 4) check link status: check, get verbose log, delete if 0
        // -----------------------------------------------------------------------------------------
        final int[] linkStatus = new int[1];         // make 1 int array, and retrieve by starting glGetProgramiv at 0
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);

        // print out the log
        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of linking program:\n" + glGetProgramInfoLog(program));
        }

        if (linkStatus[0] == 0) {
            glDeleteProgram(program);   // delete if failed
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed (linkStatus[0] == 0).");
            }
            return 0;
        }

        return program;
    }

    public static boolean validateProgram(int programObjectId) {

        // -----------------------------------------------------------------------------------------
        // 5) validate the program
        // -----------------------------------------------------------------------------------------

        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];        // make 1 int array, and retrieve by starting glGetProgramiv at 0
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

        Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }
}

