package ru.temon137.labyrintharium.World.GameObjects;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.IRenderComponent;

public class SingleRenderComponent implements IRenderComponent {
    private Bitmap bitmap;

    public SingleRenderComponent(Bitmap bitmap) {
        int coeff = (int) Settings.getCoeff();
        this.bitmap = Bitmap.createScaledBitmap(bitmap, coeff, coeff, false);
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
