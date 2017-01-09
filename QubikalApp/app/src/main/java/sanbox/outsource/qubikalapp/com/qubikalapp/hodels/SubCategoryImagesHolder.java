package sanbox.outsource.qubikalapp.com.qubikalapp.hodels;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Map;

import sanbox.outsource.qubikalapp.com.qubikalapp.R;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.ImageModel;


/**
 * Created by nguye on 12/1/2016.
 */

public class SubCategoryImagesHolder extends RecyclerView.ViewHolder {
    ImageView imgThumbnail;
    Context mContext;

    public SubCategoryImagesHolder(View itemView) {
        super(itemView);

        imgThumbnail = (ImageView) itemView.findViewById(R.id.imagetagbycategory);
        mContext = itemView.getContext();
    }

    public void bindToImage(ImageModel imageModel, View.OnClickListener starClickListener) {

        if (imageModel != null && imageModel.imagelink != null & imageModel.imagelink.trim().length() > 0){
            Log.d("image link is :", imageModel.imagelink.trim());
            Picasso.with(mContext).load(imageModel.imagelink.trim()).into(imgThumbnail);
        }
    }
}


