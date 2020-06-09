package edu.sabanicuniv.cerenanilhomework3;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter  extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    List<CommentItem> comments;
    Context context;

    public CommentsAdapter(List<CommentItem> comments, Context context) {
        this.comments = comments;
        this.context = context;

    }

    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View cm = LayoutInflater.from(context).inflate(R.layout.comments_row_layout, parent, false);
        return new CommentsViewHolder(cm);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, final int position) {

        holder.commentWriter.setText(comments.get(position).getName());
        holder.comment.setText(comments.get(position).getMessage());




    }
    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView commentWriter;
        TextView comment;
        ConstraintLayout root;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            commentWriter = itemView.findViewById(R.id.commentwriter);
            comment = itemView.findViewById(R.id.commenttitle);
            root = itemView.findViewById(R.id.container);
        }
    }

}
