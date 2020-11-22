package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import java.util.ArrayList;



public class AdminActivity extends AppCompatActivity {
    DatabaseConnection db;
    EditText search;
    ListView users;
    private MyAdapter adapter1;
    private ArrayList<Users> mProductArrayList = new ArrayList<Users>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        search = (EditText) findViewById(R.id.search);
        users = (ListView)findViewById(R.id.list_item);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new DatabaseConnection(this);

        //Add items while cursor contains data
        Cursor cursor = db.toListView();
        while(cursor.moveToNext()){
            mProductArrayList.add(new Users(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(5), cursor.getString(6)));
        }

        adapter1 = new MyAdapter(AdminActivity.this, mProductArrayList);
        users.setAdapter(adapter1);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Users> mOriginalValues;
        private ArrayList<Users> mDisplayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<Users> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llContainer;
            TextView ID, Name, Role, Status;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.three_strings, null);
                holder.llContainer = (LinearLayout)convertView.findViewById(R.id.linear);
                holder.ID = (TextView) convertView.findViewById(R.id.ID);
                holder.Name = (TextView) convertView.findViewById(R.id.name);
                holder.Role = (TextView) convertView.findViewById(R.id.role);
                holder.Status = (TextView) convertView.findViewById(R.id.status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ID.setText(String.valueOf(mDisplayedValues.get(position).id));
            holder.Name.setText(mDisplayedValues.get(position).name);
            holder.Role.setText(mDisplayedValues.get(position).role);
            holder.Status.setText(mDisplayedValues.get(position).status);

            holder.llContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AdminActivity.this, String.valueOf(mDisplayedValues.get(position).id), Toast.LENGTH_SHORT).show();
                    Intent UserInfo = new Intent(AdminActivity.this, UserInfoAdminPanelActivity.class);
                    UserInfo.putExtra("EXTRA_ID", mDisplayedValues.get(position).id);
                    startActivity(UserInfo);

                }
            });
            return convertView;
        }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Users>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Users> FilteredArrList = new ArrayList<Users>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Users>(mDisplayedValues); // saves the original data in mOriginalValues
                }
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String name = mOriginalValues.get(i).name;
                        String role = mOriginalValues.get(i).role;
                        String stat  = mOriginalValues.get(i).status;
                        if (name.toLowerCase().startsWith(constraint.toString()) ||
                                stat.toLowerCase().startsWith(constraint.toString())||
                                role.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Users(mOriginalValues.get(i).id, mOriginalValues.get(i).name,
                                    mOriginalValues.get(i).role, mOriginalValues.get(i).status));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
    }
}