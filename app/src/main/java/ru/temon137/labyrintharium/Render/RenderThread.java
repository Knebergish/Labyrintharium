package ru.temon137.labyrintharium.Render;


import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import ru.temon137.labyrintharium.ManualResetEvent;


public class RenderThread extends Thread {
    private boolean runFlag;
    private ManualResetEvent isRender;

    private final ArrayList<IRenderable> renderList;
    //=============


    public RenderThread() {
        runFlag = false;
        isRender = new ManualResetEvent(false);

        renderList = new ArrayList<>();
    }

    public void addRendereable(IRenderable renderable) {
        renderList.add(renderable);
    }

    public void removeRendereable(IRenderable renderable) {
        renderList.remove(renderable);
    }

    public void startRender() {
        isRender.set();
    }

    public void pauseRender() {
        isRender.reset();
    }

    public void stopRender() {
        isRender.reset();

        synchronized (renderList) {
            renderList.clear();
        }
        runFlag = false;
        isRender.set();
    }

    @Override
    public void run() {
        runFlag = true;

        while (runFlag) {
            isRender.waitOne();

            synchronized (renderList) {
                for (IRenderable renderable : renderList)
                    render(renderable);
            }
        }

        Log.d("RT", "Умер.");
    }

    private void render(IRenderable renderable) {
        Canvas canvas = null;
        try {
            canvas = renderable.getSurfaceHolder().lockCanvas(null);
        } finally {
            if (canvas != null) {
                synchronized (renderable.getSurfaceHolder()) {
                    renderable.render(canvas);
                }

                renderable.getSurfaceHolder().unlockCanvasAndPost(canvas);
            }
        }

    }
}



