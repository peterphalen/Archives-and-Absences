package phalen.peter.archives;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;


public class App extends Application {


    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "******", "******");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


}
