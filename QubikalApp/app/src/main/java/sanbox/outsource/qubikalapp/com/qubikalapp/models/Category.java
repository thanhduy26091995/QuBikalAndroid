package sanbox.outsource.qubikalapp.com.qubikalapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nguye on 11/25/2016.
 */
@IgnoreExtraProperties
public class Category {

    public String category;
    public Map<String,ImageModel> images;
    public Map<String,SubCategory> subCategories;

    public  Category(){

    }

    public Category(String category, Map<String,ImageModel> images, Map<String,SubCategory> subCategories) {
        this.category = category;
        this.images = images;
        this.subCategories = subCategories;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("images", images);
        result.put("subcategories",subCategories);

        return result;
    }
}
