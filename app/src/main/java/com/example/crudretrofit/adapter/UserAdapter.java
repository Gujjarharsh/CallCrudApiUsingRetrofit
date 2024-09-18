package com.example.crudretrofit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudretrofit.R;
import com.example.crudretrofit.filter.UserFilter;
import com.example.crudretrofit.user.user_models.UserRequest;

import com.example.crudretrofit.retrofit.UserRepository;
import com.example.crudretrofit.details.DetailsActivity;
import com.example.crudretrofit.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    final Context context;
    private final List<UserRequest> originalUserList;
    public final List<UserRequest> filteredUserList;
    private final int highlightColor;
    OnDeleteClickListener onDeleteClickListener;
    private UserFilter userFilter;
    public UserAdapter(Context context, List<UserRequest> userList, int highlightColor) {
        this.context = context;
        this.originalUserList = new ArrayList<>(userList);
        this.filteredUserList = new ArrayList<>(userList);
        this.highlightColor = highlightColor;
        sortUserList(originalUserList);
        // Also sort the filtered list to reflect the change
        sortUserList(filteredUserList);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserRequest user = filteredUserList.get(position);
        holder.bind(user);
        holder.setOnDetailsClickListener(user.getId());
        holder.setOnDeleteClickListener(user, position);
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {
        if (userFilter == null) {
            userFilter = new UserFilter(this,originalUserList);
        }
        return userFilter;
    }

    public void removeUser(int position) {
        if (position >= 0 && position < filteredUserList.size()) {
            filteredUserList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, filteredUserList.size());
        }
    }




    public CharSequence getHighlightedText(String text) {
        SpannableString spannableString = new SpannableString(text);
        String query = ((SearchView) ((Activity) context).findViewById(R.id.searchView)).getQuery().toString().toLowerCase();

        if (query.isEmpty()) {
            return text;
        }

        int startIndex = text.toLowerCase().indexOf(query);
        while (startIndex >= 0) {
            int endIndex = startIndex + query.length();
            spannableString.setSpan(new ForegroundColorSpan(highlightColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = text.toLowerCase().indexOf(query, endIndex);
        }

        return spannableString;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView userNameTv, userPhoneTv, userEmailTv, detailsTv, deleteTv;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            userPhoneTv = itemView.findViewById(R.id.userPhoneTv);
            userEmailTv = itemView.findViewById(R.id.userEmailTv);
            detailsTv = itemView.findViewById(R.id.detailsTv);
            deleteTv = itemView.findViewById(R.id.deleteTv);
        }

        public void bind(UserRequest user) {
            userNameTv.setText(getHighlightedText(user.getName()));
            userPhoneTv.setText(user.getPhone());
            userEmailTv.setText(user.getEmail());
        }

        public void setOnDetailsClickListener(int userId) {
            detailsTv.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constants.Id, userId);
                context.startActivity(intent);
            });
        }

        public void setOnDeleteClickListener(UserRequest user, int position) {
            deleteTv.setOnClickListener(view -> showDeleteConfirmationDialog(user, position));
        }

        private void showDeleteConfirmationDialog(UserRequest user, int position) {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("OK", (dialogInterface, i) -> deleteUser(user, position))
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        private void deleteUser(UserRequest user, int position) {

            UserRepository userRepository=new UserRepository();
            userRepository.deleteUser(user.getId(), new UserRepository.UserCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                        onDeleteClickListener.onDeleteClick(position);
                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    private void sortUserList(List<UserRequest> userList) {
        Collections.sort(userList, new Comparator<UserRequest>() {
            @Override
            public int compare(UserRequest u1, UserRequest u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}