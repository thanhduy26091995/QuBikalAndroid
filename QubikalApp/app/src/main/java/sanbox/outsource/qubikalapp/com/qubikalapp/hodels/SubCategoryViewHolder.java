package sanbox.outsource.qubikalapp.com.qubikalapp.hodels;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sanbox.outsource.qubikalapp.com.qubikalapp.R;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.SubCategory;

/**
 * Created by nguye on 12/1/2016.
 */

public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView imgThumbnail;
    TextView txtCategoryName;
    TextView txtListImage;
    Context mContext;

    public SubCategoryViewHolder(View itemView) {
        super(itemView);

        txtCategoryName = (TextView) itemView.findViewById(R.id.txtcategory);
        txtListImage = (TextView) itemView.findViewById(R.id.txtlistimage);
        imgThumbnail = (ImageView) itemView.findViewById(R.id.category_thumbnail_photo);
        mContext = itemView.getContext();
    }

    public void binhToSubCategory(SubCategory subcategory, View.OnClickListener starClickListener) {
        txtCategoryName.setText(subcategory.category);
        /*
        if (subcategory.images != null && subcategory.images.size() > 0){
            for (String key: subcategory.images.keySet()) {
                Log.d("key is :", key);
                String imageUrl = subcategory.images.get(key).imageLink.trim();
                if (imageUrl != null && imageUrl.trim().length() > 0){
                    Log.d("image link is :", imageUrl);
                    Picasso.with(mContext).load(imageUrl).into(imgThumbnail);
                    break;
                }
                
            }
        }
        */
    }
}
