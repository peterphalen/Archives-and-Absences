package phalen.peter.archives;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

//we call the overarching application class for Parse
public class App extends Application {

    public void onCreate() {
        super.onCreate();

        //API KEY, then CLIENT KEY
        //also must be defined in manifest
        Parse.initialize(this, "*****", "******");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


}
