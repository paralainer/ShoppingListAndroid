package com.paralainer.shoppinglist.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.model.Product;
import com.paralainer.shoppinglist.service.ShoppingListService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {


    private ShoppingListService shoppingListService;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        final GestureDetector gestureDetector = new GestureDetector(this.getActivity(), new SwipeDetector() {
            @Override
            public void onSwipeRight() {
                Product product = shoppingListService.nextProduct();
                if (product == null) {
                    return;
                }
                reloadFragment(false);
            }

            @Override
            public void onSwipeLeft() {
                Product product = shoppingListService.previousProduct();
                if (product == null) {
                    return;
                }
                reloadFragment(true);
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        shoppingListService = ShoppingListService.getInstance();

        TextView productNameEditor = (TextView) rootView.findViewById(R.id.product_name);
        productNameEditor.setText(shoppingListService.getCurrentProduct().getName());
        return rootView;
    }

    public void reloadFragment(boolean left) {
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager()
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragmentTransaction
                .replace(R.id.productDetailFragment,
                        ProductDetailFragment.newInstance()
                )
                .commit();
    }


}
