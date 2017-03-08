package ru.temon137.labyrintharium.Render;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import ru.temon137.labyrintharium.ManualResetEvent;


public class RenderThread extends Thread {
    private boolean runFlag;
    private ManualResetEvent isRender;

    private SurfaceView mainSurfaceView;
    private SurfaceView controllerSurfaceView;
    private IRenderable mainRenderer;
    private IRenderable controllerRenderer;
    //=============


    public RenderThread() {
        runFlag = false;
        isRender = new ManualResetEvent(false);

        mainSurfaceView = null;
        controllerSurfaceView = null;
        mainRenderer = null;
        controllerRenderer = null;
    }

    public synchronized void setMainSurfaceView(SurfaceView mainSurfaceView) {
        this.mainSurfaceView = mainSurfaceView;
    }

    public synchronized void setControllerSurfaceView(SurfaceView controllerSurfaceView) {
        this.controllerSurfaceView = controllerSurfaceView;
    }

    public synchronized void setMainRenderer(IRenderable mainRenderer) {
        this.mainRenderer = mainRenderer;
    }

    public synchronized void setControllerRenderer(IRenderable controllerRenderer) {
        this.controllerRenderer = controllerRenderer;
    }


    public synchronized void startRender() {
        isRender.set();
    }

    public synchronized void pauseRender() {
        isRender.reset();
    }

    public synchronized void stopRender() {
        isRender.reset();

        mainSurfaceView = null;
        controllerSurfaceView = null;
        mainRenderer = null;
        controllerRenderer = null;

        runFlag = false;
        isRender.set();
    }

    @Override
    public void run() {
        runFlag = true;

        while (runFlag) {
            try {
                isRender.waitOne();
            } catch (InterruptedException e) {
                stopRender();
            }

            if (mainSurfaceView != null && mainRenderer != null)
                render(mainSurfaceView, mainRenderer);

            if (controllerSurfaceView != null && controllerRenderer != null)
                render(controllerSurfaceView, controllerRenderer);
        }
    }

    private void render(SurfaceView surfaceView, IRenderable renderer) {
        Canvas canvas = null;
        try {
            canvas = surfaceView.getHolder().lockCanvas(null);
        } finally {
            if (canvas != null) {
                synchronized (surfaceView.getHolder()) {
                    renderer.render(canvas);
                }

                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}



