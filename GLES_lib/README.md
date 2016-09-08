###########################################################################################################
# README
# Android Java:
# OpenGL ES:
# library of recyclable classes:
#
###########################################################################################################
#

# OVERVIEW:
Contains java classes which can be readily reused in multiple projects.

# INDEX:
GLSurfaceView     -   basic GlSurfaceView

GLDefiningShapes  -   drawing an object

GLProjectionNCam  -   setting up a view matrix
GLRotation        -   rotating an object by multiplying a transformation matrix with the view matrix
GLOnTouch         -   motion event responses
GLUtil            -   GLES utility classes from previous projects:
                      TextureHelper       -     loading a GLES2D texture
                      TextResourceReader  -     loading a text file from R.raw resources
                      LoggerConfig        -     simple logger controller for CatView
                      GLManager           -     GLSL compiler, which utilizes TextResourceReader
