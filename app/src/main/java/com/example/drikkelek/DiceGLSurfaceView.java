package com.example.drikkelek;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class DiceGLSurfaceView extends GLSurfaceView {

    private final DiceRenderer renderer;

    public DiceGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = new DiceRenderer();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
    }
}
