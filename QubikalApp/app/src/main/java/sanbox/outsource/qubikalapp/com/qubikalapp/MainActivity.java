package sanbox.outsource.qubikalapp.com.qubikalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sanbox.outsource.qubikalapp.com.qubikalapp.commons.FireBaseQueryCommon;
import sanbox.outsource.qubikalapp.com.qubikalapp.hodels.CategoryViewHodel;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.Category;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.ImageModel;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.UserInfo;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.Users;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnAddUser;
    Button btnGetUser;
    Button btnAddCategory;
    EditText txtCategoryName;
    Button btnTagImage;
    EditText txtImageLink;

    private FirebaseRecyclerAdapter<Category, CategoryViewHodel> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    FireBaseQueryCommon mFireBaseCommon;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtCategoryName = (EditText) findViewById(R.id.categorytext);

        btnAddCategory = (Button) findViewById(R.id.button_category_post);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strCategoryName = txtCategoryName.getText().toString();
                addNewCategory("workwhiteweb",strCategoryName);

            }
        });

        txtImageLink = (EditText) findViewById(R.id.imagelinktag);
        btnTagImage = (Button)findViewById(R.id.button_tag_image);
        btnTagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUrl = txtImageLink.getText().toString();
                tagImageToCategory("workwhiteweb","-KXpiOtQT1Dq5GrP7ebc",imageUrl);
            }
        });


        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mRecycler = (RecyclerView) findViewById(R.id.recycler_categories);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

    }

    @Override
    public void onStart(){
        super.onStart();
        Query postsQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<Category, CategoryViewHodel>(Category.class, R.layout.item_category,
                CategoryViewHodel.class, postsQuery) {
            @Override
            protected void populateViewHolder(final CategoryViewHodel viewHolder, final Category model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch ListSubCategoyActivity
                        Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                        intent.putExtra(CategoriesActivity.EXTRA_CATEGORY_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.binhToCategory(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                       // DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                        //DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard(MainActivity.this);

        return false;
    }

    private View.OnClickListener getUserOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            mFireBaseCommon = new FireBaseQueryCommon();
            String userName = "workwhiteweb";
            String category = "Flowers";
            Category result = mFireBaseCommon.getDestinationCategory(category,"workwhiteweb/categories");

        }
    };

    private View.OnClickListener addCategoryOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFireBaseCommon = new FireBaseQueryCommon();

            Users mUser = new Users();
            mUser.categories = new ArrayList<Category>();
            mUser.userInfo = new UserInfo("workwhiteweb","workwhiteweb@gmail.com","","");
            mFireBaseCommon.addCategory("Cars",mUser);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void tagImageToCategory(String userName,String categoryKey,String imageUrl){
        String key = mDatabase.child("users/" + userName +  "/categories/" + categoryKey + "/images").push().getKey();

        ImageModel imageModel = new ImageModel(key,imageUrl);
        Map<String, Object> postValues = imageModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("users/" + userName +"/categories/" + categoryKey + "/images/" + key, postValues);

        Log.d("addNewCategory: ", childUpdates.toString());

        mDatabase.updateChildren(childUpdates);
    }

    private void addNewCategory(String userName,String categoryName){

        String key = mDatabase.child("users/" + userName +  "/categories").push().getKey();
        Category category = new Category();
        category.category = categoryName;
        category.images = new HashMap<>();
        category.subCategories = new HashMap<>();
        Map<String, Object> postValues = category.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("users/" + userName +"/categories/" + key, postValues);

        Log.d("addNewCategory: ", postValues.toString());

        mDatabase.updateChildren(childUpdates);
    }

    public Query getQuery(DatabaseReference databaseReference){
        Query cagegoriesQuery = databaseReference.child("users/workwhiteweb/categories")
                .limitToFirst(100);

        return cagegoriesQuery;
    }
}
