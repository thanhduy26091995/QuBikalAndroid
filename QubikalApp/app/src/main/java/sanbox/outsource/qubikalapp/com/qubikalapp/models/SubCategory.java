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
public class SubCategory {

    public String category;
    public Map<String,ImageModel> images;

    public SubCategory(){

    }

    public SubCategory(String category, Map<String,ImageModel> images) {
        this.category = category;
        this.images = images;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("images", images);

        return result;
    }
}
