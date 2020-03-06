package com.example.itesco.cetisapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link informacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link informacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class informacionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView,logistica,ofimatica,gericultura;

    private OnFragmentInteractionListener mListener;

    public informacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment informacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static informacionFragment newInstance(String param1, String param2) {
        informacionFragment fragment = new informacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_informacion, container, false);
        textView=(TextView)view.findViewById(R.id.fechaDocumentacion);
        logistica=(TextView)view.findViewById(R.id.logistica);
        ofimatica=(TextView)view.findViewById(R.id.ofimatica);
        gericultura=(TextView)view.findViewById(R.id.gericultura);


        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textView.getText().toString());
        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 0, textView.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanText);
        spanText = Spannable.Factory.getInstance().newSpannable(logistica.getText().toString());
        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 0, logistica.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logistica.setText(spanText);

        spanText = Spannable.Factory.getInstance().newSpannable(ofimatica.getText().toString());
        spanText.setSpan(new BackgroundColorSpan(0xFFDD2C00), 0, ofimatica.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ofimatica.setText(spanText);

        spanText = Spannable.Factory.getInstance().newSpannable(gericultura.getText().toString());
        spanText.setSpan(new BackgroundColorSpan(0xFF08C255), 0, gericultura.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        gericultura.setText(spanText);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
