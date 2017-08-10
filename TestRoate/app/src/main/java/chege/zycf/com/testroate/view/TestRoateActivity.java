package chege.zycf.com.testroate.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import chege.zycf.com.testroate.R;

/**
 * Created by Boy on 2017/8/8.
 *
 * @Email wang1138966371@163.com
 */

public class TestRoateActivity extends Activity implements RoundSpinView.onRoundSpinViewListener {

    private RoundSpinView roundSpinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roate);
        initView();

    }

    private void initView(){
        roundSpinView = findViewById(R.id.roundview);
        roundSpinView.setOnRoundSpinViewListener(this);
    }

    @Override
    public void onSingleTapUp(int position) {
        Toast.makeText(TestRoateActivity.this, "postion==>"+position, Toast.LENGTH_SHORT).show();
//        switch (position) {
//            case 0:
//                Toast.makeText(TestRoateActivity.this, "place:0", Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Toast.makeText(TestRoateActivity.this, "place:1", Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(TestRoateActivity.this, "place:2", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
    }
}
