package id.fikri.testrealmdatabase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fikri on 23/12/16.
 */

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {

    private ArrayList<ArticleModel> article;

    public AdapterArticle(ArrayList<ArticleModel> article) {
        this.article = article;
    }

    @Override
    public AdapterArticle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterArticle.ViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(article.get(position).getId()));
        holder.title.setText(article.get(position).getTitle());
        holder.description.setText(article.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return article.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            description = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}