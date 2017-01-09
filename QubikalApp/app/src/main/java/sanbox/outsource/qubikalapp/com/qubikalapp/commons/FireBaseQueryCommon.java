package sanbox.outsource.qubikalapp.com.qubikalapp.commons;

/**
 * Created by nguye on 11/25/2016.
 */

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sanbox.outsource.qubikalapp.com.qubikalapp.models.Category;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.SubCategory;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.Users;

public class FireBaseQueryCommon {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Users mUser;

    public FireBaseQueryCommon(){}

    public void addUser(Users aUser){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users/" + aUser.userInfo.uid).setValue(aUser.toMap());

    }

    public Users getUser(String access_token){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query userQuery = mDatabase.child("users/" + access_token)
                .limitToFirst(100);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Log.v("issue : ",issue.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("result",userQuery.toString());

        return  null;
    }

    public String addCategory(String category,Users aUser){
        /*
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //images
        List<String> mImgs = new ArrayList<>();
        mImgs.add("http://c7.alamy.com/comp/BAJDA8/orchid-flower-purple-yellow-ladys-slippers-BAJDA8.jpg");
        mImgs.add("http://c7.alamy.com/comp/EAM214/ladys-slipper-orchid-paphiopedilum-philippinense-var-roebelenii-EAM214.jpg");
        //subCategory
        List<SubCategory> mSubs = new ArrayList<>();
        mSubs.add(new SubCategory("Sub1",mImgs));
        mSubs.add(new SubCategory("Sub2",mImgs));

        //Category putCategory = new Category(category,new ArrayList<String>(),new ArrayList<SubCategory>());
        Category putCategory = new Category(category,mImgs,mSubs);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = putCategory.toMap();

        String key = mDatabase.child("users/" + aUser.userInfo.uid + "/categories").push().getKey();

        childUpdates.put("users/" + aUser.userInfo.uid + "/categories/" + key,postValues);
        mDatabase.updateChildren(childUpdates);
        */

        return "";
    }

    public String addSubCategory(String subCategory,String category,Users aUser){
        return  "";
    }

    public List<Category> findCategory(){
        return  null;
    }

    public Category getDestinationCategory(String category,String path){
        /*
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query userQuery = mDatabase.child("users/" + path)
                .equalTo("category",category);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Log.v("category : ",issue.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

        return null;
    }

    public SubCategory getDestinationSubCategory(String subCategory,String path){
        return  null;
    }

    public String tagImageCategory(String imageUrl,String category){
        return "";
    }

    public String tagImageSubCategory(String imageUrl,String subCategory,String path){
        return "";
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
