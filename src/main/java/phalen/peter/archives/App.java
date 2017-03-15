package phalen.peter.archives;

import android.app.Application;
import com.onesignal.OneSignal;



//we call the overarching application class for Parse
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }


}
