package com.example.menu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.menu.BottomSheet.Calculator;
import com.example.menu.data.Language;
import com.example.menu.data.StatisticData;
import com.example.menu.data.Statistics;
import com.example.menu.data.Transaction;
import com.example.menu.database.DB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import java.util.List;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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
//                    FileOutputStream fileOut = new FileOutputStream(file);
//                    wb.write(fileOut);
//                    wb.close();
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
                        label = new Label(i, 3, datas.get(i).getPercent() + "");
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
                        label = new Label(i, 7, datas.get(i).getPercent() + "");
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
