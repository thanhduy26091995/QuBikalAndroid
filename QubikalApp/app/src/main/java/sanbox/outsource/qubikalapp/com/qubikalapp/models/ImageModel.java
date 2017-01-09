package sanbox.outsource.qubikalapp.com.qubikalapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguye on 12/2/2016.
 */
@IgnoreExtraProperties
public class ImageModel {
    public String imagekey;
    public String imagelink;

    public  ImageModel(){

    }

    public ImageModel(String imagekey, String imagelink) {
        this.imagekey = imagekey;
        this.imagelink = imagelink;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("imagekey", imagekey);
        result.put("imagelink", imagelink);

        return result;
    }

}
