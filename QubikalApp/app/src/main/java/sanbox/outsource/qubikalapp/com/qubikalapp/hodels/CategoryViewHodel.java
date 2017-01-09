package sanbox.outsource.qubikalapp.com.qubikalapp.hodels;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sanbox.outsource.qubikalapp.com.qubikalapp.R;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.Category;
import sanbox.outsource.qubikalapp.com.qubikalapp.models.ImageModel;

/**
 * Created by nguye on 11/30/2016.
 */

public class CategoryViewHodel extends RecyclerView.ViewHolder {
    ImageView imgThumbnail;
    TextView txtCategoryName;
    TextView txtListImage;
    Context mContext;

    public CategoryViewHodel(View itemView) {
        super(itemView);

        txtCategoryName = (TextView) itemView.findViewById(R.id.txtcategory);
        txtListImage = (TextView) itemView.findViewById(R.id.txtlistimage);
        imgThumbnail = (ImageView) itemView.findViewById(R.id.category_thumbnail_photo);
        mContext = itemView.getContext();
    }

    public void binhToCategory(Category category, View.OnClickListener starClickListener) {
        txtCategoryName.setText(category.category);
        if (category.images != null && category.images.size() > 0){
            for (String imgKey: category.images.keySet()
                 ) {
                ImageModel imageModel = (ImageModel) category.images.get(imgKey);

                Picasso.with(mContext).load(imageModel.imagelink).into(imgThumbnail);
                break;
            }
        }
    }
}
