package sanbox.outsource.qubikalapp.com.qubikalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import sanbox.outsource.qubikalapp.com.qubikalapp.hodels.SubCategoryViewHolder;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.Category;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.SubCategory;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "SubCategoryActivity";

    public static final String EXTRA_CATEGORY_KEY = "category_key";
    private String mCategoryKey;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private FirebaseRecyclerAdapter<SubCategory, SubCategoryViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    EditText txtSubCategoryName;
    Button btnAddSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mCategoryKey = getIntent().getStringExtra(EXTRA_CATEGORY_KEY);
        if (mCategoryKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_CATEGORY_KEY");
        }
        else{
            Log.d(TAG, mCategoryKey);
        }

        txtSubCategoryName = (EditText) findViewById(R.id.subcategorytext);
        btnAddSubCategory  = (Button) findViewById(R.id.button_subcategory_post) ;
        btnAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSubCategory("workwhiteweb",txtSubCategoryName.getText().toString());
            }
        });

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mRecycler = (RecyclerView) findViewById(R.id.recycler_subcategory);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
    }

    @Override
    public  void onStart(){
        super.onStart();

        Query postsQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<SubCategory, SubCategoryViewHolder>(SubCategory.class, R.layout.item_category,
                SubCategoryViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final SubCategoryViewHolder viewHolder, final SubCategory model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                final  String subName = model.category;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoriesActivity.this, TagSubCategoryActivity.class);
                        intent.putExtra(TagSubCategoryActivity.EXTRA_CATEGORY_KEY, postKey);
                        intent.putExtra(TagSubCategoryActivity.EXTRA_CATEGORY_NAME, subName);
                        intent.putExtra(TagSubCategoryActivity.EXTRA_PARENTCATEGORY_KEY, mCategoryKey);
                        startActivity(intent);

                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.binhToSubCategory(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public Query getQuery(DatabaseReference databaseReference){
        Query cagegoriesQuery = databaseReference.child("users/workwhiteweb/categories/" + mCategoryKey + "/subcategories")
                .limitToFirst(100);

        return cagegoriesQuery;
    }

    private void addNewSubCategory(String userName,String categoryName){

        String key = mDatabase.child("users/" + userName +  "/categories/" + mCategoryKey + "/subcategories").push().getKey();

        SubCategory subCategory = new SubCategory();
        subCategory.category = categoryName;
        subCategory.images = new HashMap<>();
        Map<String, Object> postValues = subCategory.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("users/" + userName +"/categories/" + mCategoryKey + "/subcategories/" + key, postValues);

        Log.d("addNewSubCategory: ", postValues.toString());

        mDatabase.updateChildren(childUpdates);
    }
}
