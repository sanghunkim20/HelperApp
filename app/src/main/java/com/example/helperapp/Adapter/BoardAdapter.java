package com.example.helperapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.helperapp.R;
import com.example.helperapp.Shop.Board;


import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private List<Board> boardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String boardName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BoardAdapter(List<Board> boardList) {
        this.boardList = boardList;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.board, parent, false);
        return new BoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board board = boardList.get(position);

        if (holder.boardNameTextView != null) {
            holder.boardNameTextView.setText(board.getEt_name());
        }

        if (holder.boardTelTextView != null) {
            holder.boardTelTextView.setText(board.getEt_telenum());
        }

        if (holder.boardTitleTextView != null) {
            holder.boardTitleTextView.setText(board.getEt_title());
        }

        if (holder.boardConTextView != null) {
            holder.boardConTextView.setText(board.getEt_content());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(board.getEt_name());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        public TextView boardNameTextView;
        public TextView boardTelTextView;
        public TextView boardConTextView;
        public TextView boardTitleTextView;

        public BoardViewHolder(View view) {
            super(view);
            boardNameTextView = view.findViewById(R.id.et_name);
            boardTelTextView = view.findViewById(R.id.et_title);
            boardConTextView = view.findViewById(R.id.et_telenum);
            boardTitleTextView = view.findViewById(R.id.et_content);
        }
    }
}