package fr.telecom.wall_ed.view;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import fr.telecom.wall_ed.R;


public class SessionFragment extends Fragment {

    private TextView mTxtView_stats;
    private Button mBt_stop;
    private View.OnClickListener mOnClickListenerCallback;

    public SessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mOnClickListenerCallback = (View.OnClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " View.OnClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate view
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        //TextView setup
        mTxtView_stats = view.findViewById(R.id.session_txt_stats);
        //Button setup
        mBt_stop = view.findViewById(R.id.session_bt_stop);
        mBt_stop.setOnClickListener(mOnClickListenerCallback);
        return view;
    }
}
