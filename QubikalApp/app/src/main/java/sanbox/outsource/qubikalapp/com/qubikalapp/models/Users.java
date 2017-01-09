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
public class Users {

    public List<Category> categories;
    public UserInfo userInfo;

    public Users(){

    }

    public Users(List<Category> categories, UserInfo userInfo) {
        this.categories = categories;
        this.userInfo = userInfo;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("userinfo", userInfo);

        return result;
    }
}
