package inexus.dev.agstream.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;

import inexus.dev.agstream.Interface.ItemClickListener;
import inexus.dev.agstream.R;

/**
 * Created by iNexus on 2/20/2018.
 */

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public RibbonLayout ribbonLayout;
    public TextView txtTitle, txtTag;
    public ImageView imageView;

    private ItemClickListener itemClickListener;


    public DataViewHolder(View itemView) {
        super(itemView);

        ribbonLayout = (RibbonLayout)itemView.findViewById(R.id.ribbonLayout);
        imageView = (ImageView)itemView.findViewById(R.id.image_data);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
