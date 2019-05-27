package ftnjps.recipes.data;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseJobService extends JobService {

    private static final String TAG = "DatabaseJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started...");
        executeJob(params);
        // Returning {@code false} from this method means your job is already finished.
        // Return {@code true} from this method if your job needs to continue running.
        // We tell our system that it should keep the device awake until our services finish work
        // We are responsible with telling the system when we are finished so it can release the lock
        return true;
    }

    private void executeJob(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Job running...");
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
                                DatabaseInstance.getInstance(getApplicationContext()).recipeDao().insertOne(r);
                            } catch (SQLiteConstraintException uniqueConstraintException) {
                                System.out.println("RECIPE ALREADY EXISTS IN THE DATABASE");
                                uniqueConstraintException.printStackTrace();
                            } catch (Exception e) {
                                System.out.println("EXCEPTION WHEN ADDING NEW RECIPE IN THE DATABASE");
                                e.printStackTrace();
                            }
                        }
                        //DatabaseInstance.getInstance(getApplicationContext()).recipeDao().deleteAll();
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

                Log.d(TAG, "Job finished...");
                jobFinished(params, false);
            }
        }).start();


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job canceled before completion...");
        // {@code true} to indicate to the JobManager whether you'd like to reschedule
        // this job based on the retry criteria provided at job creation-time; or {@code false}
        // to end the job entirely.
        return false;
    }
}
