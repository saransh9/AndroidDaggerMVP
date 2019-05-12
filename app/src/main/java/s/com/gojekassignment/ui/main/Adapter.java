package s.com.gojekassignment.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.databinding.ItemListBinding;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

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
            mGithubModelArrayList.get(position).setChildVisible(!mGithubModelArrayList.get(position).isChildVisible());
            notifyItemChanged(position);
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


    public class ViewHolder extends RecyclerView.ViewHolder {


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
