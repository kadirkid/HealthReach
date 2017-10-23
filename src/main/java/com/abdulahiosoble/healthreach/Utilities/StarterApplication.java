package com.abdulahiosoble.healthreach.Utilities;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("4e188aaea9fd210545126d11e6dc4b2463265310")
                .clientKey("00f12c54779ba75af4d6deb7c599c5de77ddd47a")
                .server("http://ec2-13-59-193-44.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );


        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

}
