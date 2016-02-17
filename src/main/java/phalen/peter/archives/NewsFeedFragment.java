package phalen.peter.archives;

/**
 * Created by PeterPhalen on 2/10/16.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@SuppressLint("ValidFragment")
public class NewsFeedFragment extends Fragment {
    private WebView mWebView;
    private boolean mIsWebViewAvailable;
    ProgressDialog progress;
    private String mUrl = "*****";

    /**
     * Creates a new fragment which loads the supplied url as soon as it can
     */
    @SuppressLint("ValidFragment")
    public NewsFeedFragment(String url) {
        super();
        mUrl = url;
    }



    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(getActivity());
        View myFragmentView = inflater.inflate(R.layout.news_feed_fragment, container, false);
        progress = ProgressDialog.show(getActivity(), "Loading", "Please wait...", true);


        mWebView.setWebViewClient(new InnerWebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (progress != null) {
                    progress.dismiss();
                }
                super.onPageFinished(view, url);

            }


        }); // forces it to open in app
        mWebView.loadUrl(mUrl);
        mIsWebViewAvailable = true;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        return mWebView;
    }

    /**
     * Convenience method for loading a url. Will fail if {@link View} is not initialised (but won't throw an {@link Exception})
     */
    public void loadUrl( ) {
        if (mIsWebViewAvailable) getWebView().loadUrl( mUrl );
        else Log.w("ImprovedWebViewFragment", "WebView cannot be found. Check the view and fragment have been loaded.");
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    /* To ensure links open within the application */
    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }


    }
}