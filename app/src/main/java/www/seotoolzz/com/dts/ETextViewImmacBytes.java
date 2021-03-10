package www.seotoolzz.com.dts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ETextViewImmacBytes extends androidx.appcompat.widget.AppCompatEditText {

    public ETextViewImmacBytes(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ETextViewImmacBytes(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ETextViewImmacBytes(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/product_sans.ttf");
            setTypeface(tf);
        }
    }

}