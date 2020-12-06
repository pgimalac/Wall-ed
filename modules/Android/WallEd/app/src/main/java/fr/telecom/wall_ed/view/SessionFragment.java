package fr.telecom.wall_ed.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import fr.telecom.wall_ed.R;

/**
 * Ce fragment gère la durée de vies des différents fragments de l'application.
 */

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

    private void createCallbackToParentActivity() {
        try {
            // Parent activity will automatically subscribe to callback
            mOnClickListenerCallback = (View.OnClickListener)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() +
                                         " View.OnClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate view
        View view =
            inflater.inflate(R.layout.fragment_session, container, false);
        // TextView setup
        mTxtView_stats = view.findViewById(R.id.session_txt_stats);
        // Button setup
        mBt_stop = view.findViewById(R.id.session_bt_stop);
        mBt_stop.setOnClickListener(mOnClickListenerCallback);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (SessionFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (SessionFragment) : destroyed");
    }
}
