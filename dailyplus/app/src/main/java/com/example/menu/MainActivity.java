package com.example.menu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.menu.BottomSheet.Calculator;
import com.example.menu.data.Language;
import com.example.menu.data.StatisticData;
import com.example.menu.data.Statistics;
import com.example.menu.data.Transaction;
import com.example.menu.database.DB;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//test fetch

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    public static String user;
    public static List<Transaction> transactions;


    private LoginButton loginButton;
    private CircleImageView profileIcon;
    private TextView xmail,xname;
    private CallbackManager callbackManager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = getIntent().getStringExtra("user");
        transactions = loadingPage.db.queryAllTransactions(user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Calculator calculator = new Calculator();
            calculator.show(getSupportFragmentManager(), "Calculator bottom sheet");
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bill, R.id.nav_slideshow,R.id.nav_categories,
                R.id.nav_language,R.id.nav_chart,
                R.id.nav_tools, R.id.nav_share, R.id.nav_password, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(listener);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


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
                profileIcon.setImageResource(R.drawable.applogo);
//                Toast.makeText(MainActivity.this,"User Logged Out",Toast.LENGTH_LONG).show();
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



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.nav_send) {
                try {
                    File root = android.os.Environment.getExternalStorageDirectory();
                    File dir = new File (root.getAbsolutePath() + "/download");
                    dir.mkdirs();
                    File file = new File(dir, "statistics.xls");
                    if (!file.exists())
                        file.createNewFile();
                    WritableWorkbook workbook = Workbook.createWorkbook(file); // 创建一个可读写的副本
                    workbook.createSheet("sheet0", 0);
                    WritableSheet sheet = workbook.getSheet(0);

                    Label l = new Label(0, 0, Language.expense());
                    WritableCellFeatures wcf = new WritableCellFeatures();
                    l.setCellFeatures(wcf);
                    sheet.addCell(l);
                    List<StatisticData> datas = Statistics.getExpenseStatisticData(MainActivity.transactions);
                    for (int i = 0; i < datas.size(); i++) {
                        Label label = new Label(i, 1, datas.get(i).getCategory());
                        WritableCellFeatures wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                        label = new Label(i, 2, datas.get(i).getAmount() + "");
                        wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                        String percent = new DecimalFormat("0.##").format(datas.get(i).getPercent() * 100);
                        label = new Label(i, 3, percent);
                        wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                    }

                    Label l2 = new Label(0, 4, Language.income());
                    WritableCellFeatures wcf2 = new WritableCellFeatures();
                    l2.setCellFeatures(wcf2);
                    sheet.addCell(l2);
                    datas = Statistics.getIncomeStatisticData(MainActivity.transactions);
                    for (int i = 0; i < datas.size(); i++) {
                        Label label = new Label(i, 5, datas.get(i).getCategory());
                        WritableCellFeatures wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                        label = new Label(i, 6, datas.get(i).getAmount() + "");
                        wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                        String percent = new DecimalFormat("0.##").format(datas.get(i).getPercent() * 100);
                        label = new Label(i, 7, percent);
                        wcfeatures = new WritableCellFeatures();
                        label.setCellFeatures(wcfeatures);
                        sheet.addCell(label);
                    }
                    workbook.write();
                    workbook.close();
                    ViewParent parent = navigationView.getParent();
                    if (parent instanceof DrawerLayout) {
                        ((DrawerLayout) parent).closeDrawer(navigationView);
                    }
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "body");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    System.out.println(new File(dir, "statistics.xls").getAbsolutePath());
                    intent.setType("application/excel");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/download/statistics.xls"));
                    Intent.createChooser(intent, "Choose Email Client");
                    PackageManager packageManager = getPackageManager();
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "There is no mail app to send the mail.", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } else {
                if (item.getItemId() == R.id.nav_home) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                if (handled) {
                    ViewParent parent = navigationView.getParent();
                    if (parent instanceof DrawerLayout) {
                        ((DrawerLayout) parent).closeDrawer(navigationView);
                    }
                }
            }
            return true;
        }
    };

}
