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

import java.text.DecimalFormat;
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
//
//
//
//    public void createPieChart(List<String> titleArr, List<String> fldNameArr, List<Map<String, Object>> dataList) {
//        SXSSFSheet sheet = wb.createSheet("sheet2");
//        boolean result = drawSheet2Map(sheet, "is3D", fldNameArr, dataList, titleArr);
//        System.out.println("生成饼状图(普通or3D)-->" + result);
//    }
//    /**
//     * 创建饼状图
//     *
//     * @param sheet
//     *            页签
//     * @param type
//     *            图类型(3D或者普通)
//     * @param fldNameArr
//     *            (类标题)
//     * @param dataList
//     *            (填充数据)
//     * @param titleArr
//     *            (标题)
//     * @return
//     */
//    private boolean drawSheet2Map(SXSSFSheet sheet, String type, List<String> fldNameArr,
//                                  List<Map<String, Object>> dataList, List<String> titleArr) {
//        boolean result = false;
//        // 获取sheet名称
//        String sheetName = sheet.getSheetName();
//        result = drawSheet0Table(sheet, titleArr, fldNameArr, dataList);
//        // 创建一个画布
//        Drawing<?> drawing = sheet.createDrawingPatriarch();
//        // 画一个图区域
//        // 前四个默认0，从第8行到第25行,从第0列到第6列的区域
//        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 6, 25);
//        // 创建一个chart对象
//        Chart chart = drawing.createChart(anchor);
//        CTChart ctChart = ((XSSFChart) chart).getCTChart();
//        CTPlotArea ctPlotArea = ctChart.getPlotArea();
//        CTBoolean ctBoolean = null;
//        CTPie3DChart ctPie3DChart = null;
//        CTPieChart ctPieChart = null;
//        // 创建饼状图模型
//        if (type.equals("is3D")) {
//            ctPie3DChart = ctPlotArea.addNewPie3DChart();
//            ctBoolean = ctPie3DChart.addNewVaryColors();
//        } else {
//            ctPieChart = ctPlotArea.addNewPieChart();
//            ctBoolean = ctPieChart.addNewVaryColors();
//        }
//        // 创建序列,并且设置选中区域
//        for (int i = 0; i < fldNameArr.size() - 1; i++) {
//            CTPieSer ctPieSer = null;
//            if (type.equals("is3D")) {
//                ctPieSer = ctPie3DChart.addNewSer();
//            } else {
//                ctPieSer = ctPieChart.addNewSer();
//            }
//            CTSerTx ctSerTx = ctPieSer.addNewTx();
//            // 图例区
//            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
//            // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
//            String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
//            ctStrRef.setF(legendDataRange);
//            ctPieSer.addNewIdx().setVal(i);
//            // 横坐标区
//            CTAxDataSource cttAxDataSource = ctPieSer.addNewCat();
//            ctStrRef = cttAxDataSource.addNewStrRef();
//            // 选第0列,第1-6行作为横坐标区域
//            String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
//            ctStrRef.setF(axisDataRange);
//            // 数据区域
//            CTNumDataSource ctNumDataSource = ctPieSer.addNewVal();
//            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
//            // 选第1-6行,第1-3列作为数据区域 //1 2 3
//            String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
//                    true);
//            System.out.println(numDataRange);
//            ctNumRef.setF(numDataRange);
//            // 显示边框线
//            ctPieSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] { 0, 0, 0 });
//            // 设置标签格式
//            ctBoolean.setVal(true);
//        }
//        // legend图注
//        CTLegend ctLegend = ctChart.addNewLegend();
//        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
//        ctLegend.addNewOverlay().setVal(true);
//        return result;
//    }
//
//    private boolean drawSheet0Table(SXSSFSheet sheet, List<String> titleArr, List<String> fldNameArr,
//                                    List<Map<String, Object>> dataList) {
//        boolean result = true;
//        List<CellStyle> styleList = tableStyle();
//        SXSSFRow row0 = sheet.createRow(0);
//        for (int i = 0; i < titleArr.size(); i++) {
//            // 设置标题
//            row0.createCell(i).setCellValue(titleArr.get(i));
//            // 设置标题行样式
//            row0.getCell(i).setCellStyle(styleList.get(0));
//        }
//        // 填充数据
//        for (int i = 0; i < dataList.size(); i++) {
//            // 获取每一项的数据
//            Map<String, Object> data = dataList.get(i);
//            // 设置每一行的字段标题和数据
//            SXSSFRow rowi = sheet.createRow(i + 1);
//            for (int j = 0; j < data.size(); j++) {
//                // 判断是否是标题字段列
//                if (j == 0) {
//                    rowi.createCell(j).setCellValue((String) data.get("value" + (j + 1)));
//                    // 设置左边字段样式
//                    sheet.getRow(i + 1).getCell(j).setCellStyle(styleList.get(0));
//                } else {
//                    rowi.createCell(j).setCellValue(Double.valueOf((String) data.get("value" + (j + 1))));
//                    // 设置数据样式
//                    sheet.getRow(i + 1).getCell(j).setCellStyle(styleList.get(2));
//                }
//            }
//        }
//        return result;
//    }
}
