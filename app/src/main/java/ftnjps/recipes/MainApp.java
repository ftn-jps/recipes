package ftnjps.recipes;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainApp extends Application {

    // SET CHECKING THE FIREBASE SERVER TO EVERY 15 MINUTES
    public static long JOB_REFRESH_INTERVAL = TimeUnit.MINUTES.toMillis(15);

    @Override
    public void onCreate() {
        super.onCreate();

        //DatabaseInstance.getInstance(getApplicationContext()).clearAllTables();
        //DatabaseInstance.getInstance(getApplicationContext()).recipeDao().deleteAll();

        // KONEKCIJA SA FIREBASE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("recipes");

        // LISTENER KOJI POKUPI RECEPTE SA FIREBASE-A I SMESTI IH U BAZU
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Recipe r = dataSnapshot.getValue(Recipe.class);
                if(DatabaseInstance.getInstance(getApplicationContext()).recipeDao().findById(r.getId()) == null) {
                    try {
                        // KADA SE POKUPI OBJEKAT SA FIREBASE-A, PROVERI SE ID DA LI POSTOJI TAKAV U BAZI, I SMESTA SE
                        r.setCreationDate(new Date());
                        DatabaseInstance.getInstance(getApplicationContext()).recipeDao().insertOne(r);
                    } catch (SQLiteConstraintException uniqueConstraintException) {
                        System.out.println("RECIPE ALREADY EXISTS IN THE DATABASE");
                        uniqueConstraintException.printStackTrace();
                    } catch (Exception e) {
                        System.out.println("EXCEPTION WHEN ADDING NEW RECIPE IN THE DATABASE");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // NAKON STO PRVI PUT POKUPI SVE I SMESTI U BAZU, NAKON TOGA NA SVAKIH 15 MIN PROVERAVA
        scheduleJob();

        /* adding to Firebase database as 1 value
        // myRef.setValue(r1);

        // ADDING TO FIREBASE TO RECIPES LIST
        myRef.push().setValue(r1);
        myRef.push().setValue(r2);
        myRef.push().setValue(r3);
        myRef.push().setValue(r4);
        myRef.push().setValue(r5); */
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, DatabaseJobService.class);
        JobInfo jobInfoObj = new JobInfo.Builder(1, componentName)
                .setPeriodic(JOB_REFRESH_INTERVAL)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(jobInfoObj);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("# DatabaseJobService #", "scheduleJob: success");
        } else {
            Log.d("# DatabaseJobService #", "scheduleJob: failed");
        }
    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }
}
