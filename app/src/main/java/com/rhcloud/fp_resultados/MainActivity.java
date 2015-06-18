package com.rhcloud.fp_resultados;

        import android.app.Activity;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.webkit.WebChromeClient;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ProgressBar;



public class MainActivity extends Activity
{
    static final String miURL = "http://fp-resultados.rhcloud.com/";
    private WebView miWebView;
    private ProgressBar progressBar;
    String ua = "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:35.0) Gecko/20100101 Firefox/35.0";

    public void onBackPressed()
    {
        if (this.miWebView.canGoBack())
        {
            this.miWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    public void onCreate(Bundle paramBundle)
    {

        super.onCreate(paramBundle);
        this.miWebView = new WebView(this);
        WebSettings localWebSettings = this.miWebView.getSettings();
        localWebSettings.setUserAgentString(this.ua);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setDatabaseEnabled(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setLoadWithOverviewMode(true);
        this.miWebView.setScrollBarStyle(33554432);
        this.miWebView.setWebViewClient(new MiWebViewClient());
        this.miWebView.loadUrl(miURL);
        setContentView(this.miWebView);
        this.progressBar = new ProgressBar(this);
        this.miWebView.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
            {
                MainActivity.this.progressBar.setProgress(0);
                MainActivity.this.progressBar.setVisibility(0);
                MainActivity.this.setProgress(paramAnonymousInt * 1000);
                MainActivity.this.progressBar.incrementProgressBy(paramAnonymousInt);
                if (paramAnonymousInt == 100)
                    MainActivity.this.progressBar.setVisibility(8);
            }
        });
    }

    private class MiWebViewClient extends WebViewClient
    {
        private MiWebViewClient()
        {
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
            if (paramString.endsWith(".pdf"))
            {
                Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
                localIntent.setFlags(4194304); // FLAG_ACTIVITY_BROUGHT_TO_FRONT
                MainActivity.this.startActivity(localIntent);
                return true;
            }
            paramWebView.loadUrl(paramString);
            return false;
        }
    }
}
