package org.androidtown.tab;
//
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import static org.androidtown.tab.R.layout.fragment1;

/**
 * Created by EOM on 2015-08-20.
 */
public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(fragment1, container, false);

        return rootView;
    }
}
