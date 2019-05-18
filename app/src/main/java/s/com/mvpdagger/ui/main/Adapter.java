package s.com.mvpdagger.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import s.com.mvpdagger.data.model.GithubModel;
import s.com.mvpdagger.databinding.ItemListBinding;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    int currentExpanded = -1;
    private ArrayList<GithubModel> mGithubModelArrayList;
    private Context mContext;

    public Adapter(Context context, ArrayList<GithubModel> githubModelArrayList) {
        this.mGithubModelArrayList = githubModelArrayList;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        ItemListBinding binding = ItemListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(mGithubModelArrayList.get(position));
        Glide.with(mContext)
                .load((mGithubModelArrayList.get(position).getBuiltBy())[0].getAvatar())
                .centerCrop()
                .into(holder.binding.civProfile);

        holder.binding.clParent.setOnClickListener(v -> {
            if (currentExpanded != -1 && currentExpanded != position) {
                mGithubModelArrayList.get(currentExpanded).setChildVisible(!mGithubModelArrayList.get(currentExpanded).isChildVisible());
                notifyItemChanged(currentExpanded);
                if (mGithubModelArrayList.size() > position + 1) {
                    mGithubModelArrayList.get(currentExpanded + 1).setShadowVisible(!mGithubModelArrayList.get(currentExpanded + 1).isShadowVisible());
                    notifyItemChanged(currentExpanded + 1);
                }
            }
            if (currentExpanded == position) {
                currentExpanded = -1;
            } else {
                currentExpanded = position;
            }
            mGithubModelArrayList.get(position).setChildVisible(!mGithubModelArrayList.get(position).isChildVisible());
            notifyItemChanged(position);
            if (mGithubModelArrayList.size() > position + 1) {
                mGithubModelArrayList.get(position + 1).setShadowVisible(!mGithubModelArrayList.get(position + 1).isShadowVisible());
                notifyItemChanged(position + 1);
            }


        });
    }

    @Override
    public int getItemCount() {
        if (mGithubModelArrayList != null) {
            return mGithubModelArrayList.size();
        } else {
            return 0;
        }
    }

    public ArrayList<GithubModel> getmGithubModelArrayList() {
        return this.mGithubModelArrayList;
    }

    public void setmGithubModelArrayList(ArrayList<GithubModel> mGithubModelArrayList) {
        currentExpanded = -1;
        for (int temp = 0; temp < mGithubModelArrayList.size(); temp++) {
            if (mGithubModelArrayList.get(temp).isShadowVisible()) {
                mGithubModelArrayList.get(temp).setShadowVisible(false);
            }
        }
        for (int temp = 0; temp < mGithubModelArrayList.size(); temp++) {
            if (mGithubModelArrayList.get(temp).isChildVisible()) {
                currentExpanded = temp;
                if (mGithubModelArrayList.size() > temp + 1) {
                    mGithubModelArrayList.get(temp + 1).setShadowVisible(true);
                }
            }
        }
        this.mGithubModelArrayList = new ArrayList<>(mGithubModelArrayList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private final ItemListBinding binding;

        ViewHolder(ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(GithubModel item) {
            binding.setData(item);
            binding.executePendingBindings();

        }
    }
}
