package com.example.android.animatedrecycler;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.ViewHolder mViewHolder;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        mLayoutManager = new LinearLayoutManager(getContext());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.startLayoutAnimation();
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Log.d("[Test] onChildViewAttachedToWindow", view.toString());
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Log.d("[Test] onChildViewDetachedFromWindow", view.toString());

            }
        });
        LayoutAnimationController controller = mRecyclerView.getLayoutAnimation();


        mRecyclerView.setLayoutAnimation(

                new MyLayoutAnimationController(
                        controller.getAnimation(),
                        controller.getDelay()
                )
        );
        mRecyclerView.addOnLayoutChangeListener(
                new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        View child = ((RecyclerView) v).getChildAt(0);
                        Log.d("[Test] onLayoutChange", v.toString());

                    }
                }

        );
        return rootView;
    }

    public static class MyLayoutAnimationController extends LayoutAnimationController {


        public MyLayoutAnimationController(Animation animation, float delay) {
            super(animation, delay);
        }

        @Override
        protected int getTransformedIndex(AnimationParameters params) {
            if (params.index <= 1) {
                return 0;
            }
            return params.index;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public int index;

        public MyViewHolder(ImageView itemView, int index) {
            super(itemView);
            mImageView = itemView;
            this.index = index;
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        int positions = 255 / 15;

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item, parent, false);
            return new MyViewHolder(imageView, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mImageView.setBackgroundColor(Color.rgb(position * 15, 0, 0));
        }

        @Override
        public int getItemCount() {
            return positions;
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
