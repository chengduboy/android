package chege.zycf.com.testroate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.CarrouselRotateDirection;
import com.dalong.carrousellayout.OnCarrouselItemClickListener;
import com.dalong.carrousellayout.OnCarrouselItemSelectedListener;

import chege.zycf.com.testroate.view.TestRoateActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private SeekBar mSeekBarR;
    private SeekBar mSeekBarX;
    private SeekBar mSeekBarZ;
    private CheckBox mCheckbox;
    private Button btn_roate;
    private Switch mSwitchLeftright;
    private int width;
    private CarrouselLayout carrousel;
    private SeekBar mSeekBarTime;
    private int[] imageres = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4,
            R.mipmap.image5, R.mipmap.image6, R.mipmap.image7, R.mipmap.image8, R.mipmap.image9};
    private boolean[] select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        select = new boolean[imageres.length];
        initView();
        initLinstener();
    }

    private void initView() {
        carrousel = (CarrouselLayout) findViewById(R.id.carrousel);
        for (int res : imageres) {
            addImageView(res);
        }
//        addTextView("1");
//        addTextView("2");
//        addTextView("3");
//        addTextView("4");
        mSeekBarR = (SeekBar) findViewById(R.id.seekBarR);
        mSeekBarX = (SeekBar) findViewById(R.id.seekBarX);
        mSeekBarZ = (SeekBar) findViewById(R.id.seekBarZ);
        mSeekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        btn_roate = (Button) findViewById(R.id.btn_roate);
        btn_roate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestRoateActivity.class));
            }
        });
        mSwitchLeftright = (Switch) findViewById(R.id.switchLeftright);
        mSeekBarX.setProgress(mSeekBarX.getMax() / 2);
        mSeekBarZ.setProgress(mSeekBarZ.getMax() / 2);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        carrousel.setR(width / 3)//设置R的大小
                .setAutoRotation(false)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
    }


    private void addImageView(int res) {
        ImageView image = new ImageView(this);
        image.setImageResource(res);
        carrousel.addView(image);
    }

    private void addTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(50);
        carrousel.addView(tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启自动
        carrousel.resumeAutoRotation();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //停止自动
        carrousel.stopAutoRotation();
    }

    private void initLinstener() {
        carrousel.setOnCarrouselItemClickListener(new OnCarrouselItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                carrousel.setSelectItem(position);
            }

        });
        /**
         * 选中回调
         */
        carrousel.setOnCarrouselItemSelectedListener(new OnCarrouselItemSelectedListener() {
            @Override
            public void selected(View view, int position) {
                Log.v(TAG, "position:" + position);
                for(int i=0;i<carrousel.getChildCount();i++){
                    ImageView img = (ImageView) carrousel.getChildAt(i);
                    ViewGroup.LayoutParams ps = img.getLayoutParams();
                    ps.height = 200;
                    ps.width = 200;
                    img.setLayoutParams(ps);
                }
                ImageView image = (ImageView) view;
                ViewGroup.LayoutParams ps = image.getLayoutParams();
                ps.height = 300;
                ps.width = 300;
                image.setLayoutParams(ps);
            }
        }
    );
    /**
     * 设置旋转事件间隔
     */
    mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

    {
        @Override
        public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
        long time = (long) (1.0f * progress / seekBar.getMax() * 800);
        if (time <= 100) time = 100;
        carrousel.setAutoRotationTime(time);
    }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){
    }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){
    }
    }

    );
    /**
     * 设置子半径R
     */
    mSeekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

    {
        @Override
        public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
        float r = 1.f * progress / seekBar.getMax() * width;
        carrousel.setR(r <= 0 ? 1 : r);
        carrousel.refreshLayout();
    }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){
    }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){
    }
    }

    );
    /**
     * X轴旋转
     */
    mSeekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

    {
        @Override
        public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
        carrousel.setRotationX(progress - seekBar.getMax() / 2);
        carrousel.refreshLayout();
    }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){
    }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){
    }
    }

    );
    /**
     * Z轴旋转
     */
    mSeekBarZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

    {
        @Override
        public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
        carrousel.setRotationZ(progress - seekBar.getMax() / 2);
        carrousel.refreshLayout();
    }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){

    }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){
    }
    }

    );
    /**
     * 设置是否自动旋转
     */
    mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
        carrousel.setAutoRotation(isChecked);//启动LoopViewPager自动切换
    }
    }

    );
    /**
     * 设置向左还是向右自动旋转
     */
    mSwitchLeftright.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
        carrousel.setAutoScrollDirection(isChecked ? CarrouselRotateDirection.anticlockwise
                : CarrouselRotateDirection.clockwise);
    }
    }

    );
}
}
