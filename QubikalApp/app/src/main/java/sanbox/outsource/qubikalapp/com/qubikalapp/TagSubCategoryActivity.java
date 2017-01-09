package sanbox.outsource.qubikalapp.com.qubikalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sanbox.outsource.qubikalapp.com.qubikalapp.hodels.SubCategoryImagesHolder;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.ImageModel;

public class TagSubCategoryActivity extends AppCompatActivity {

    private static final String TAG = "TagSubCategoryActivity";

    public static final String EXTRA_CATEGORY_KEY = "subcategory_key";
    public static final String EXTRA_CATEGORY_NAME = "subcategory_name";
    public static final String EXTRA_PARENTCATEGORY_KEY = "parentcategory_key";
    private String mSubCategoryKey;
    private String mSubCategoryName;
    private String mParentCategory;

    TextView txtSubCategoryName;
    Button btnTagImage;
    EditText txtImageLink;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private FirebaseRecyclerAdapter<ImageModel, SubCategoryImagesHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private StaggeredGridLayoutManager mLayoutManager;
    ScaleAnimation shrinkAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_sub_category);

        mSubCategoryKey = getIntent().getStringExtra(EXTRA_CATEGORY_KEY);
        if (mSubCategoryKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_CATEGORY_KEY");
        }
        else{
            Log.d(TAG, mSubCategoryKey);
        }

        mParentCategory = getIntent().getStringExtra(EXTRA_PARENTCATEGORY_KEY);
        if (mParentCategory == null) {
            throw new IllegalArgumentException("Must pass EXTRA_PARENTCATEGORY_KEY");
        }
        else{
            Log.d(TAG, mParentCategory);
        }

        mSubCategoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        if (mSubCategoryName == null) {
            throw new IllegalArgumentException("Must pass EXTRA_CATEGORY_KEY");
        }
        else{
            Log.d(TAG, mSubCategoryName);
        }

        txtSubCategoryName = (TextView)findViewById(R.id.txtsubcategorytext);
        if (mSubCategoryName != null){
            txtSubCategoryName.setText(mSubCategoryName);
        }

        txtImageLink = (EditText)findViewById(R.id.txtimagelink);

        btnTagImage = (Button) findViewById(R.id.button_tag_imge_tosubcategory);
        btnTagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagImageSubCategory("workwhiteweb",txtImageLink.getText().toString());
            }
        });

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mRecycler = (RecyclerView) findViewById(R.id.listimagetagcategory);
        mRecycler.setHasFixedSize(true);

        //scale animation to shrink floating actionbar
        shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        if (mRecycler != null) {
            //to enable optimization of recyclerview
            mRecycler.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mLayoutManager);

        FirebaseRecyclerAdapter<ImageModel,ImagewViewHolder> adapter = new FirebaseRecyclerAdapter<ImageModel, ImagewViewHolder>(
                ImageModel.class,
                R.layout.image_board_item,
                ImagewViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabase.child("users").child("workwhiteweb").child("categories").child(mParentCategory)
                        .child("subcategories").child(mSubCategoryKey).child("images").getRef()
        ) {
            @Override
            protected void populateViewHolder(ImagewViewHolder viewHolder, ImageModel model, int position) {

                Picasso.with(TagSubCategoryActivity.this).load(model.imagelink).into(viewHolder.imageDisplay);
            }
        };

        mRecycler.setAdapter(adapter);
        /*
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        */
    }

    @Override
    public  void onStart(){
        super.onStart();

        /*
        Query postsQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<ImageModel, SubCategoryImagesHolder>(ImageModel.class, R.layout.item_image_tags,
                SubCategoryImagesHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final SubCategoryImagesHolder viewHolder, final ImageModel model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                ImageModel data = model;
                if (data != null){
                    Log.v("link is: ", data.imagelink);
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToImage(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
        */
    }

    //ViewHolder for our Firebase UI
    public static class ImagewViewHolder extends RecyclerView.ViewHolder{
        ImageView imageDisplay;

        public ImagewViewHolder(View v) {
            super(v);
            imageDisplay = (ImageView) v.findViewById(R.id.img_post_subcategory);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    private void tagImageSubCategory(String userName,String imageLink){

        String key = mDatabase.child("users/" + userName +  "/categories/" + mParentCategory + "/subcategories/" + mSubCategoryKey + "/images").push().getKey();


        ImageModel imageModel = new ImageModel();
        imageModel.imagekey = key;
        imageModel.imagelink = imageLink;

        Map<String, Object> postValues = imageModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("users/" + userName +"/categories/" + mParentCategory + "/subcategories/" + mSubCategoryKey + "/images/" + key, postValues);


        //Map<String,Object> postValues = new HashMap<>();
        //postValues.put(key,imageLink);

        //Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("users/" + userName +"/categories/" + mParentCategory + "/subcategories/" + mSubCategoryKey + "/images/" + key, postValues);

        //Log.d("tag images: ", postValues.toString());
        Log.d("addNewCategory: ", childUpdates.toString());

        mDatabase.updateChildren(childUpdates);
    }

    public Query getQuery(DatabaseReference databaseReference){
        String fullQuery = "users/workwhiteweb/categories/" + mParentCategory + "/subcategories/" + mSubCategoryKey +"/images";
        Log.d("query string is: ", "getQuery: " + fullQuery);
        Query cagegoriesQuery = databaseReference.child("users/workwhiteweb/categories/" + mParentCategory + "/subcategories/" + mSubCategoryKey +"/images")
                .limitToFirst(100);

        return cagegoriesQuery;
    }
}
