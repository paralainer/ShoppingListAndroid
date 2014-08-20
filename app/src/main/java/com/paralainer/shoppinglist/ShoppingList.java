package com.paralainer.shoppinglist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ShoppingList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ShoppingListFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ShoppingListFragment extends Fragment {

        private EditText addToListText;
        private ArrayAdapter<String> shoppingListAdapter;
        private ArrayList<String> shoppingList;

        public ShoppingListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
            ImageButton button = (ImageButton) rootView.findViewById(R.id.addToListButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddToShoppingListButtonClick(view);
                }
            });

            addToListText  = (EditText) rootView.findViewById(R.id.addToListText);
            shoppingList = new ArrayList<String>();
            shoppingListAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, shoppingList);
            ListView shoppingList  = (ListView) rootView.findViewById(R.id.shoppingListView);
            shoppingList.setAdapter(shoppingListAdapter);
            return rootView;
        }

        public void onAddToShoppingListButtonClick(View view){
            shoppingListAdapter.add(addToListText.getText().toString());
        }
    }
}
