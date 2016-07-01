package com.zk.weldmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zk.EventBus.EventData;
import com.zk.EventBus.EventPic;
import com.zk.EventBus.EventRbs;
import com.zk.adapter.ProduceStatusAdapter;
import com.zk.entity.DataTag;
import com.zk.entity.ProduceStatus;
import com.zk.entity.RobotData;
import com.zk.service.ScalService;
import com.zk.task.MyTask;
import com.zk.task.MyTaskInterface;
import com.zk.utils.DividerItemDecoration;
import com.zk.utils.FormatUtil;
import com.zk.utils.L;
import com.zk.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.lv_produce)
    RecyclerView lvProduce;
    @BindView(R.id.tv_weld1State)
    TextView tvWeld1State;
    @BindView(R.id.tv_weld2State)
    TextView tvWeld2State;
    @BindView(R.id.tv_weld3State)
    TextView tvWeld3State;
    @BindView(R.id.tv_materialrobotState)
    TextView tvMaterialrobotState;
    @BindView(R.id.tv_carrierState)
    TextView tvCarrierState;
    @BindView(R.id.lc_weldelec)
    LineChart lcWeldelec;
    @BindView(R.id.lc_weldvol)
    LineChart lcWeldvol;
    @BindView(R.id.iv_attendanceHead1)
    ImageView ivAttendanceHead1;
    @BindView(R.id.tv_attendanceName1)
    TextView tvAttendanceName1;
    @BindView(R.id.tv_attendanceId1)
    TextView tvAttendanceId1;
    @BindView(R.id.iv_attendanceHead2)
    ImageView ivAttendanceHead2;
    @BindView(R.id.tv_attendanceName2)
    TextView tvAttendanceName2;
    @BindView(R.id.tv_attendanceId2)
    TextView tvAttendanceId2;
    @BindView(R.id.iv_attendanceHead3)
    ImageView ivAttendanceHead3;
    @BindView(R.id.tv_attendanceName3)
    TextView tvAttendanceName3;
    @BindView(R.id.tv_attendanceId3)
    TextView tvAttendanceId3;
    @BindView(R.id.tv_proProduced)
    TextView tvProProduced;
    @BindView(R.id.tv_proTotal)
    TextView tvProTotal;
    @BindView(R.id.tv_proRemain)
    TextView tvProRemain;
    @BindView(R.id.tv_weld4State)
    TextView tvWeld4State;
    @BindView(R.id.tv_weld5State)
    TextView tvWeld5State;
    @BindView(R.id.tv_weld6State)
    TextView tvWeld6State;

    private List<ProduceStatus> datas;
    private List<TextView> statusTextviews;
    private List<RobotData> robotDatas ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        robotDatas = new ArrayList<RobotData>();


        initProduceData();
        initView();


        //连接数据库
        // 异步连接数据库 判断是否联网
        MyTask myTask = new MyTask(new MyTaskInterface() {

            @Override
            public void doUi() {
                // TODO Auto-generated method stub
                if (DataTag.MYSQL_CONNECT_FLAG) {
                    L.e("数据库已连接");
                } else {
                    Toast.makeText(MainActivity.this, "网络错误", 0).show();
                }
            }

            @Override
            public String[] doBackGround() {
                // TODO Auto-generated method stub
                Util.onConn();
                return null;
            }
        });

        myTask.execute("conn");

        //初始话数据图格式和数据
        initChartWidget(lcWeldelec);
        initChartWidget(lcWeldvol);
        setData(20, 40, lcWeldelec);
        setData(20, 50, lcWeldvol);

        startService(new Intent(MainActivity.this , ScalService.class));
    }






    /*
     加载图片
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoadHeadPic(EventPic eventPic) {

        if (eventPic.getPic()!=null) {
            ivAttendanceHead1.setImageBitmap(Util.Bytes2Bimap(eventPic.getPic()));
        }
    }

    /*
        折线图数据获取
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initChartDatas(EventData eventData) {
            //增加采集的数据到list中

        if (eventData.getData() != null){
            robotDatas.add(eventData.getData());
        }

    }

    /*
    初始化生产情况列表
         */
    private void initProduceData() {

        datas = new ArrayList<ProduceStatus>();
        for (int i = 0; i < 10; i++) {
            ProduceStatus ps = new ProduceStatus();
            ps.setId("" + i);
            ps.setName("工件" + i);
            ps.setTime(FormatUtil.refFormatNowDate());
            ps.setStatus("正常");
            datas.add(ps);
        }

    }

    private void initView() {

        //状态ui
        statusTextviews = new ArrayList<TextView>();
        statusTextviews.clear();
        statusTextviews.add(tvWeld1State);
        statusTextviews.add(tvWeld2State);
        statusTextviews.add(tvWeld3State);
        statusTextviews.add(tvWeld4State);
        statusTextviews.add(tvWeld5State);
        statusTextviews.add(tvWeld6State);
        statusTextviews.add(tvCarrierState);
        statusTextviews.add(tvMaterialrobotState);
        //设置RecylerView为list效果并设置分割线
        lvProduce.setLayoutManager(new LinearLayoutManager(this));
        lvProduce.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ProduceStatusAdapter mAdapter = new ProduceStatusAdapter(this, datas);
        lvProduce.setAdapter(mAdapter);
    }

    private void initChartWidget(LineChart lcWeldelec) {
        lcWeldelec.setDescription("");
        lcWeldelec.setNoDataTextDescription("无数据或服务未连接");

        //set background color
        lcWeldelec.setBackgroundColor(Color.LTGRAY);
        //enable touch gestures
        lcWeldelec.setTouchEnabled(true);
        lcWeldelec.setDragDecelerationFrictionCoef(0.9f);

        //enable scaling and dragging

        lcWeldelec.setDragEnabled(true);
        lcWeldelec.setScaleEnabled(true);
        lcWeldelec.setDrawGridBackground(false);
        lcWeldelec.setHighlightPerDragEnabled(true);

        //animation start
        lcWeldelec.animateX(1500);

        //legend
        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Legend l = lcWeldelec.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextSize(15f);
        l.setTextColor(Color.WHITE);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        //axis
        XAxis xAxis = lcWeldelec.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = lcWeldelec.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(900f);
        leftAxis.setAxisMinValue(-200f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = lcWeldelec.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaxValue(900);
        rightAxis.setAxisMinValue(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }


    /*
     初始化actionbar
     */
    private void initActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.titlebar);
        actionBar.setIcon(R.drawable.mjlogo);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

    }

    /*
       初始化曲线图数据
     */
    private void setData(int count, float range, LineChart mChart) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(new Entry(val, i));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 450;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals2.add(new Entry(val, i));
        }

        ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 250;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals3.add(new Entry(val, i));
        }

        ArrayList<Entry> yVals4 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 150;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals4.add(new Entry(val, i));
        }

        ArrayList<Entry> yVals5 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 590;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals5.add(new Entry(val, i));
        }

        ArrayList<Entry> yVals6 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 120;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals6.add(new Entry(val, i));
        }

        LineDataSet set1, set2, set3, set4, set5, set6;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);
            set5 = (LineDataSet) mChart.getData().getDataSetByIndex(4);
            set6 = (LineDataSet) mChart.getData().getDataSetByIndex(5);

            set1.setYVals(yVals1);
            set2.setYVals(yVals2);
            set3.setYVals(yVals3);
            set4.setYVals(yVals4);
            set5.setYVals(yVals5);
            set6.setYVals(yVals6);
            mChart.getData().setXVals(xVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset 1 and give it a type
            set1 = new LineDataSet(yVals1, "焊机1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset2 and give it a type
            set2 = new LineDataSet(yVals2, "焊机2");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a dataset 3 and give it a type
            set3 = new LineDataSet(yVals3, "焊机3");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(Color.YELLOW);
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));
            //set3.setFillFormatter(new MyFillFormatter(900f));

            // create a dataset 4 and give it a type
            set4 = new LineDataSet(yVals4, "焊机4");
            set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set4.setColor(Color.BLUE);
            set4.setCircleColor(Color.WHITE);
            set4.setLineWidth(2f);
            set4.setCircleRadius(3f);
            set4.setFillAlpha(65);
            set4.setFillColor(Color.BLUE);
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));
            //set4.setFillFormatter(new MyFillFormatter(900f));

            // create a dataset 5 and give it a type
            set5 = new LineDataSet(yVals5, "焊机5");
            set5.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set5.setColor(getResources().getColor(R.color.black));
            set5.setCircleColor(Color.WHITE);
            set5.setLineWidth(2f);
            set5.setCircleRadius(3f);
            set5.setFillAlpha(65);
            set5.setColor(getResources().getColor(R.color.black));
            set5.setDrawCircleHole(false);
            set5.setHighLightColor(Color.rgb(244, 117, 117));
            //set5.setFillFormatter(new MyFillFormatter(900f));

            // create a dataset 6 and give it a type
            set6 = new LineDataSet(yVals6, "焊机6");
            set6.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set6.setColor(getResources().getColor(R.color.moccasin));
            set6.setCircleColor(Color.WHITE);
            set6.setLineWidth(2f);
            set6.setCircleRadius(3f);
            set6.setFillAlpha(65);
            set6.setColor(getResources().getColor(R.color.moccasin));
            set6.setDrawCircleHole(false);
            set6.setHighLightColor(Color.rgb(244, 117, 117));
            //set6.setFillFormatter(new MyFillFormatter(900f));

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            dataSets.add(set3);
            dataSets.add(set4);
            dataSets.add(set5);
            dataSets.add(set6);

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateRobotStateUi(EventRbs event) {

        if (event.getRs() != null) {
            L.e("eventBUS 机器人状态更新");
            if (event.getRs().getWeld1() == 1) {
                tvWeld1State.setText("正常");
                setDrawableRight(tvWeld1State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld1State.setText("停止");
                setDrawableRight(tvWeld1State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getWeld2() == 1) {
                tvWeld2State.setText("正常");
                setDrawableRight(tvWeld2State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld2State.setText("停止");
                setDrawableRight(tvWeld2State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getWeld3() == 1) {
                tvWeld3State.setText("正常");
                setDrawableRight(tvWeld3State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld3State.setText("停止");
                setDrawableRight(tvWeld3State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getWeld4() == 1) {
                tvWeld4State.setText("正常");
                setDrawableRight(tvWeld4State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld4State.setText("停止");
                setDrawableRight(tvWeld4State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getWeld5() == 1) {
                tvWeld5State.setText("正常");
                setDrawableRight(tvWeld5State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld5State.setText("停止");
                setDrawableRight(tvWeld5State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getWeld6() == 1) {
                tvWeld6State.setText("正常");
                setDrawableRight(tvWeld6State, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvWeld6State.setText("停止");
                setDrawableRight(tvWeld6State, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getCarrier() == 1) {
                tvCarrierState.setText("正常");
                setDrawableRight(tvCarrierState, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvCarrierState.setText("停止");
                setDrawableRight(tvCarrierState, getResources().getDrawable(R.mipmap.red_circle));
            }

            if (event.getRs().getMaterial() == 1) {
                tvMaterialrobotState.setText("正常");
                setDrawableRight(tvMaterialrobotState, getResources().getDrawable(R.mipmap.green_circle));
            } else {
                tvMaterialrobotState.setText("停止");
                setDrawableRight(tvMaterialrobotState, getResources().getDrawable(R.mipmap.red_circle));
            }

        } else {
            for (TextView tv : statusTextviews) {
                tv.setText("离线");
                setDrawableRight(tv, getResources().getDrawable(R.mipmap.gray_circle));
            }
        }


    }

    /*
        设置drawableRight属性
     */
    public void setDrawableRight(TextView view, Drawable drawable) {

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(MainActivity.this , ScalService.class));
    }
}
