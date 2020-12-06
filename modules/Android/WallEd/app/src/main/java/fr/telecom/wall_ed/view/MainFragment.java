package fr.telecom.wall_ed.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import fr.telecom.wall_ed.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener mOnClickListenerCallback;
    private Button mDemarrerButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =
            inflater.inflate(R.layout.fragment_main, container, false);
        mDemarrerButton = result.findViewById(R.id.demarrer_button);
        mDemarrerButton.setOnClickListener(this);
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    @Override
    public void onClick(View v) {

        mOnClickListenerCallback.onClick(v);
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
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (MainFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (MainFragment) : destroyed");
    }
}
