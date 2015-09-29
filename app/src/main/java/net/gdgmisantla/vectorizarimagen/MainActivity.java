package net.gdgmisantla.vectorizarimagen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    Bitmap Imag;
    ImageView view;
    TextView text;
    int picw, pich;
    int pix[];
    int x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DragRectView view2 = (DragRectView) findViewById(R.id.dragRect);
        Imag = BitmapFactory.decodeResource(getResources(), R.drawable.rgb);
        picw = Imag.getWidth();
        pich = Imag.getHeight();
        view = (ImageView) findViewById(R.id.image);
        view.setImageBitmap(Imag);
        text = (TextView) findViewById(R.id.tex);
        pix = new int[picw * pich];

        if (null != view2) {
            view2.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {
                    x1 = rect.left;
                    y1 = rect.top;
                    x2 = rect.right;
                    y2 = rect.bottom;

                    Toast.makeText(getApplicationContext(), "Coordenadas (" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bitmap bm = Bitmap.createBitmap(picw, pich, Bitmap.Config.ARGB_4444);
        switch (item.getItemId()) {
            case R.id.normalMenu:
                view.setImageBitmap(Imag);
                return true;
            case R.id.canalesRojo:
                pix = ChannelRed(Imag);
                bm.setPixels(pix, 0, picw, 0, 0, picw, pich);
                view.setImageBitmap(bm);
                view.invalidate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int[] ChannelRed(Bitmap mBitmap) {
        int picw, pich;
        int sx = x1;
        int sy = y1;
        int fx = x2;
        int fy = y2;
        Toast.makeText(getApplicationContext(), "Coordenadas (" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")", Toast.LENGTH_LONG).show();
        picw = mBitmap.getWidth();
        pich = mBitmap.getHeight();
        int[] pix = new int[picw * pich];
        mBitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
        for (int y = sy; y < fy; y++)
            for (int x = sx; x < fx; x++) {
                int index = (y * picw) + x;
                int r = (pix[index] >> 16) & 0xff;
                pix[index] = 0xff000000 | (r << 16) | (0 << 8) | 0;
            }
        return pix;
    }
}
