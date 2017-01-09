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
public class UserInfo {
    public String uid;
    public String username;
    public String email;
    public String avatar;
    public String access_token;

    public UserInfo(){

    }

    public UserInfo(String uid, String username, String email, String avatar) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
    }

    public UserInfo(String uid, String username, String email, String avatar, String access_token) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.access_token = access_token;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email",email);
        result.put("avatar",avatar);
        result.put("access_token",access_token);

        return result;
    }
}
