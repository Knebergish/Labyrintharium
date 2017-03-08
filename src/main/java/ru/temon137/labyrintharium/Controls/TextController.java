package ru.temon137.labyrintharium.Controls;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.temon137.labyrintharium.ManualResetEvent;
import ru.temon137.labyrintharium.Render.IRenderable;
import ru.temon137.labyrintharium.World.World;


public class TextController implements IController, IRenderable {
    private List<String> stringList;
    private ManualResetEvent endStrings;
    private int textSize = 30;

    public TextController(String... strings) {
        this.stringList = new ArrayList<>();
        Collections.addAll(this.stringList, strings);

        endStrings = new ManualResetEvent(false);
    }

    @Override
    public void handleEvent(MotionEvent event) {
        StandartController standartController = new StandartController();
        Control.setController(standartController);
        World.getRenderThread().setControllerRenderer(standartController);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (stringList.isEmpty())
            return;

        Spanned myString = new SpannedString(stringList.get(0));

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        StaticLayout sl = new StaticLayout(myString.subSequence(0, myString.length()), textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
        sl.draw(canvas);
    }
}
