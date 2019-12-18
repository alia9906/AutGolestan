package ir.ateck.autogolestan;

public class Pixel {
    private final float x ,y;
    private int srgbcolor = 0x00000000;
    public Pixel(float x , float y , int color){
        this.x = x;
        this.y = y;
        this.srgbcolor = color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return srgbcolor;
    }

    public void setColor(int color){
        this.srgbcolor = color;
    }
}
