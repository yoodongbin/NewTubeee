package org.androidtown.basic.widget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * 기본 위젯 사용하기
 *
 * - 앱을 실행한 후 원하는 버튼을 하나 선택하면 그 위젯을 사용한 화면이 보입니다.
 * - 화면이 어떻게 만들어졌는지는 res/layout 폴더 밑의 해당 XML 레이아웃 파일을 보시면 됩니다.
 *
 * @author Mike
 *
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 첫번째 버튼을 눌렀을 때 textview.xml 에 정의된 화면 레이아웃을 보여줍니다.
     * @param v
     */
    public void onButton1Clicked(View v) {
        setContentView(R.layout.textview);
    }

    /**
     * 두번째 버튼을 눌렀을 때 button.xml 에 정의된 화면 레이아웃을 보여줍니다.
     * @param v
     */
    public void onButton2Clicked(View v) {
        setContentView(R.layout.button);
    }

    /**
     * 세번째 버튼을 눌렀을 때 edittext.xml 에 정의된 화면 레이아웃을 보여줍니다.
     * @param v
     */
    public void onButton3Clicked(View v) {
        setContentView(R.layout.edittext);
    }

    /**
     * 네번째 버튼을 눌렀을 때 image.xml 에 정의된 화면 레이아웃을 보여줍니다.
     * @param v
     */
    public void onButton4Clicked(View v) {
        setContentView(R.layout.image);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
