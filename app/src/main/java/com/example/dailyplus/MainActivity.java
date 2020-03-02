package com.example.dailyplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailyplus.ui.home_fragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.logging.Logger;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    private LoginButton loginButton;
    private CircleImageView profileIcon;
    private TextView xmail,xname;
    private CallbackManager callbackManager;
    private static final String key = "key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        loginButton = header.findViewById(R.id.login_btn);
        profileIcon = header.findViewById(R.id.profile_icon);
        xmail = header.findViewById(R.id.profile_email);
        xname = header.findViewById(R.id.profile_name);
        System.out.println("001"+xname.getText().toString());
        loginButton.setOnClickListener(e->
        {
            System.out.println("Login Button Clicked");


        });


        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null)
            {
                xmail.setText(R.string.default_email);
                xname.setText(R.string.default_name);
                profileIcon.setImageResource(R.drawable.app);
                Toast.makeText(MainActivity.this,"User Logged Out",Toast.LENGTH_LONG).show();
            }
            else{
                loadUserProfile(currentAccessToken);
            }
        }
    };

    public void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    xmail.setText(email);
                    xname.setText(first_name+" "+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    Glide.with(MainActivity.this).load(image_url).into(profileIcon);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new home_fragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
}
