package com.avas.firebase_tictactoewithimportantfirebasefeatures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int RC_SIGN_IN = 69;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    AuthUI authUi;


    FirebaseDatabase database;
    DatabaseReference reference, gameDataReference;
    User user;
    public static final String USERS = "Users";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String GAME = "Game";


    Button loginButton, playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        authUi = AuthUI.getInstance();

        mUser = mAuth.getCurrentUser();



        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USERS);
        gameDataReference = database.getReference(GAME);


        if(mUser!=null){
            //we add the user's info to our realtime database
            reference.child(mUser.getUid());
            reference.child(mUser.getUid()).child(EMAIL).setValue(mUser.getEmail());
            reference.child(mUser.getUid()).child(NAME).setValue(mUser.getDisplayName());

            //also adding the user's game info to our realtime database
            user = new User(mUser.getDisplayName(),mUser.getEmail(),mUser.getUid());
            gameDataReference.child(mUser.getUid()).setValue(user);

        }

        loginButton = findViewById(R.id.loginButton);
        playButton = findViewById(R.id.playButton);



        logInMethod();

    }

    private void logInMethod() {

        //checking if the user is signed in, if not then start the FirebaseUI activity
        if (mUser == null) {
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build());

            //starting the firebaseUI activity
            startActivityForResult(
                    authUi.createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);

        }

    }

    public void logInButtonClicked(View v) {
        logInMethod();

    }

    public void playButtonClicked(View v) {
        //clicking playButton takes you to the activity where you can choose the player you want to play against
        Intent intent = new Intent(this, MatchmakerActivity.class);
        startActivity(intent);
    }

    //loading the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating the layout to the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem logOutMenu = menu.findItem(R.id.logout);
        logOutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mUser != null) {
                    mUser = null;
                    Log.d(TAG, "onMenuItemClick: User logged out");
                    Toast.makeText(MainActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "No user is signed in", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            //we can get the response from the server in IdpResponse variable, it'll help us debug if there's any error
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                mUser = mAuth.getCurrentUser();
                Log.d(TAG, "onActivityResult: User signed in: " + mUser.getEmail());
                Toast.makeText(this, mUser.getEmail() + " signed in", Toast.LENGTH_SHORT).show();

                //we add the user's info to our realtime database
                reference.child(mUser.getUid());
                reference.child(mUser.getUid()).child(EMAIL).setValue(mUser.getEmail());
                reference.child(mUser.getUid()).child(NAME).setValue(mUser.getDisplayName());

                //also adding the user's game info to our realtime database
                user = new User(mUser.getDisplayName(),mUser.getEmail(),mUser.getUid());
                gameDataReference.child(mUser.getUid()).setValue(user);


            } else {
                Log.d(TAG, "onActivityResult: Sign in failed " + response.getError().getErrorCode());
                Toast.makeText(this, "Sign in not successful", Toast.LENGTH_SHORT).show();
            }
        }
    }
}