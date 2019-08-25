package top.defaults.colorpicker;

public class OnColorChanged {
    private int color = 0;
    private ColorChangeListener listener;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if (listener != null) listener.onChange(this.color);
    }

    public ColorChangeListener getListener() {
        return listener;
    }

    public void setListener(ColorChangeListener listener) {
        this.listener = listener;
    }

    public interface ColorChangeListener {
        void onChange(int color);
    }
}