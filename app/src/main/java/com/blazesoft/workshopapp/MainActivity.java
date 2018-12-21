package com.blazesoft.workshopapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blazesoft.workshopapp.activities.ApprovalActivity;
import com.blazesoft.workshopapp.adapters.PageAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.datastore.LocalDatabase;
import com.blazesoft.workshopapp.interfaces.LogInListener;
import com.blazesoft.workshopapp.models.MyApplication;
import com.blazesoft.workshopapp.models.UserRoles;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LogInListener{

    View loginView,approval_layout;
    private EditText username;
    private UserLoginTask mAuthTask = null;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    private EditText Password;
    DrawerLayout drawer;
    Button btnloginhere;
    List<UserRoles> userRoles=new ArrayList<>();
    private static LogInListener logInListener;
    ProgressBar progress_bar;
    static ImageView nav_profile_photo,sort;
    static TextView textView,nav_welcome,userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar=findViewById(R.id.progress_bar);
        nav_profile_photo=findViewById(R.id.nav_profile_photo);
        Password=findViewById(R.id.passwords);
        username=findViewById(R.id.Username);
        nav_welcome=findViewById(R.id.nav_welcome);
        userType=findViewById(R.id.userType);
        textView=findViewById(R.id.nav_profile_name);

        btnloginhere=findViewById(R.id.register);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        btnloginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Password !=null && username!=null) {
                    attemptLogin();

                    // landingpageView.setVisibility(View.VISIBLE);
                    //  loginView.setVisibility(View.GONE);

                }else{

                    Toast.makeText(MainActivity.this, "Password and username are required",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            super.onBackPressed();
//        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    private boolean attemptLogin() {

        // Reset errors.
        username.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
        String email = username.getText().toString();
//                username.getText().toString();
        String password = Password.getText().toString();
//                Password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) ) {
//            Password.setError(getString(R.string.error_invalid_password));
//            focusView = Password;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        } else if (!isEmailValid(email)) {
            username.setError(getString(R.string.error_invalid_email));
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if(!NetworkConnection.isConnectedToInternet(this.getApplication())){
                Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.VISIBLE);
                // btnloginhere.setEnabled(true);
                return true;
            }
            progress_bar.setVisibility(View.VISIBLE);
            //    btnloginhere.setEnabled(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

        }

        return false;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        JSONObject jsonPerson= new JSONObject();
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            try {
                jsonPerson.put("email", username.getText().toString());
                jsonPerson.put("password", Password.getText().toString());
                jsonPerson.put("device", android.os.Build.MODEL);
                jsonPerson.put("source", "android");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            NetworkConnection.makeAPostRequest(URL.USER_LOGIN, jsonPerson.toString(), null, new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {
                    Toast.makeText(MainActivity.this, "Unable to login ,check your network and try again", Toast.LENGTH_SHORT).show();

                    //  btnloginhere.setEnabled(true);
                    progress_bar.setVisibility(View.GONE);
                    //   btnloginhere.setVisibility(View.VISIBLE);
                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }
                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                    username.setEnabled(true);
                    progress_bar.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                        if (jsonObject.getString("status").equals("201")){
                            Toast.makeText(MainActivity.this, "Wrong Username Or Password", Toast.LENGTH_SHORT).show();
                        }else{
                            String token=jsonObject.getString("access_token");
                            LocalDatabase.setToken(MainActivity.this,token);
                            JSONArray jsonArray = jsonObject.getJSONArray("user_roles");

                            UserRoles userRoles1 = UserRoles.getUserRolesFrom(jsonObject.toString());
                          String rolename=  userRoles1.getUser_roles().get(0).getName();
                            Log.d("jsonObject.toString()",jsonObject.toString());
//                            for (int i = 0; i < jsonArray.length(); i++) {
                                userRoles.add(userRoles1);
                             //   Log.d("rolesArray", jsonArray.getJSONObject(i).toString());
                             //   String userroles=userRoles1.getUser_roles().get(i).getName();

                               LocalDatabase.setUserRoles(MainActivity.this,rolename);
//                            }
                            String RolesforLogin=LocalDatabase.getUserRoles(MainActivity.this);
                            String TokesnForLogi=LocalDatabase.getToken(MainActivity.this);
                            if(TokesnForLogi!=null){
                                if(RolesforLogin!=null){
                                Intent intent= new Intent(MainActivity.this,ApprovalActivity.class);
                                startActivity(intent);
                                progress_bar.setVisibility(View.GONE);
                               finish();
                                }

                            }else{
                                Toast.makeText(MainActivity.this,"Unauthorised Users", Toast.LENGTH_SHORT).show();

                            }

//
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NetworkConnection.remoteResponseLogger(remoteResponse);
                }

                @Override
                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }
            });
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
    public static  void getProfilePicture(final Person person, AppCompatActivity appCompatActivity){
        NetworkConnection.downloadImage(URL.USER_PROFILE_PIC, GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {

            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                person.setProfileByte(remoteResponse.getResponseBody());
                nav_profile_photo.setImageBitmap(person.getProfileByte());
                NetworkConnection.remoteResponseLogger(remoteResponse);
                // Toast.makeText(appCompatActivity, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }
        });
    }
    public  static void fetchUser(final AppCompatActivity appCompatActivity){
        NetworkConnection.makeAGetRequest(URL.USER_ACCOUNT, GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {

            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                try {
                    JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                    Person person=Person.getPerson(jsonObject.getString("data"));
                    getProfilePicture(person,appCompatActivity);
                  //  MyApplication.setPerson(person);
                    textView.setText(person.getName());

                    nav_welcome.setText(person.getEmail());
//                    if ( this.getLogInListener()!=null){
//                        LoginFragment.getLogInListener().onLogin(person);
//
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkConnection.remoteResponseLogger(remoteResponse);
            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }
            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);
            }
            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);
            }
        });
    }

    // Checking whethe user have logged in and check role then redirecting  user to   appropriate page.
    @Override
    protected void onResume() {
        if (!(LocalDatabase.getToken(this)).equals(LocalDatabase.NOT_LOGGED_IN)) {

            Intent intent = new Intent(MainActivity.this, ApprovalActivity.class);
            startActivity(intent);
            finish();
        }

        super.onResume();
    }
    @Override
    public void onLogin(Person person) {

    }
}
