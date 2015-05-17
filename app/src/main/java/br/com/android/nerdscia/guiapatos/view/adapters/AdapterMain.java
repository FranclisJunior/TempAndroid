package br.com.android.nerdscia.guiapatos.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.android.nerdscia.guiapatos.R;
import br.com.android.nerdscia.guiapatos.view.util.DrawerIconView;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private int focusedItem = 0;
    private String mNavTitles[];
    private int mIcons [];



    public AdapterMain(String Titles[], int Icons[]) {
        mNavTitles = Titles;
        mIcons = Icons;
    }
    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType);
            return vhItem;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(AdapterMain.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }



    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView textView;
        DrawerIconView imageView;

        public ViewHolder(final View itemView,int ViewType) {
            super(itemView);
            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (DrawerIconView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }
        }
    }

}