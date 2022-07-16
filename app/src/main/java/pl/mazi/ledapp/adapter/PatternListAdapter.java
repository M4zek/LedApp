package pl.mazi.ledapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.dialog.MyDialog;
import pl.mazi.ledapp.model.PatternModel;

import java.util.ArrayList;

public class PatternListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PatternModel> patternModels;

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private ConstraintLayout layout;
        private ImageView icon;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_titlePattern);
            layout = itemView.findViewById(R.id.constraintLayout);
            icon = itemView.findViewById(R.id.imageView);
        }
    }

    public PatternListAdapter(ArrayList<PatternModel> patternModels) {
        this.patternModels = patternModels;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;

        final PatternModel item = patternModels.get(position);

        itemHolder.tv_title.setText(item.getTitle());
        itemHolder.icon.setImageResource(item.getIcon());

        itemHolder.layout.setOnClickListener(event->{
            new MyDialog.Builder(event.getContext())
                    .pattern(item)
                    .build()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return patternModels.size();
    }
}
