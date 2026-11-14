package ir.greenhouse.assistant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {
    private WebView web;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        web.setBackgroundColor(0xFFF5F7F4);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        web.addJavascriptInterface(new PdfBridge(), "Android");
        web.loadUrl("file:///android_asset/index.html");
        setContentView(web);
    }

    private class PdfBridge {
        @JavascriptInterface
        public void printPdf() {
            runOnUiThread(() -> {
                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                if (printManager != null) {
                    String jobName = "توصیه‌های گلخانه‌ای";
                    printManager.print(jobName,
                            web.createPrintDocumentAdapter(jobName),
                            new PrintAttributes.Builder().build());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
