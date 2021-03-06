package pyxis.uzuki.live.nyancatsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import pyxis.uzuki.live.nyancat.NyanCat;
import pyxis.uzuki.live.nyancat.NyanCatGlobal;
import pyxis.uzuki.live.nyancat.printer.CatLoggerPrinter;
import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.base.InjectActivity;

/**
 * NyanCat
 * Class: JavaActivity
 * Created by Pyxis on 2017-10-30.
 * <p>
 * Description:
 */

public class JavaActivity extends InjectActivity {
    private @BindView TextView txtLogText;
    private @BindView ScrollView scrollView;
    private @BindView ImageView imageNyanCat;

    private CatLoggerPrinter textPrinter = new CatLoggerPrinter() {
        @Override
        public void println(int priority, @NotNull String tag, @NotNull String message, @org.jetbrains.annotations.Nullable Throwable t) {
            StringBuilder builder = new StringBuilder();
            builder.append("tag = ")
                    .append(tag)
                    .append(" message = ")
                    .append(message);

            if (t != null) {
                builder.append(Log.getStackTraceString(t));
            }

            txtLogText.setText(txtLogText.getText() + "\n" + builder.toString());
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        Glide.with(this).asGif().load(R.drawable.original).into(imageNyanCat);

        CustomLogFilePrinter logFilePrinter = new CustomLogFilePrinter(this, "Log.txt");

        NyanCatGlobal.addPrinter(textPrinter);
        NyanCatGlobal.addPrinter(logFilePrinter);

        txtLogText.setText("===========");

        imageNyanCat.setOnClickListener(view -> NyanCat.e("Nyan!"));
    }
}
