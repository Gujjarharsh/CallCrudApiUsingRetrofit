package com.example.crudretrofit.filter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Filter;
import com.example.crudretrofit.adapter.UserAdapter;
import com.example.crudretrofit.user.user_models.UserRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserFilter extends Filter {
    private final UserAdapter adapter;
    private final List<UserRequest> originalUserList;
    private final List<UserRequest> filteredUserList;

    public UserFilter(UserAdapter adapter, List<UserRequest> originalUserList) {
        this.adapter = adapter;
        this.originalUserList = originalUserList;
        this.filteredUserList = new ArrayList<>(originalUserList);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterPattern = constraint == null ? "" : constraint.toString().toLowerCase().trim();
        List<UserRequest> matchedUsers = new ArrayList<>();

        for (UserRequest user : originalUserList) {
            String userName = user.getName().toLowerCase();

            if (userName.startsWith(filterPattern)) {

                matchedUsers.add(user); // Add users starting with the filter
            }
        }

        // Sort matched users alphabetically
        Collections.sort(matchedUsers, new Comparator<UserRequest>() {
            @Override
            public int compare(UserRequest u1, UserRequest u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        // Add remaining users that contain the filter but don't start with it
        for (UserRequest user : originalUserList) {
            String userName = user.getName().toLowerCase();
            if (!userName.startsWith(filterPattern) && userName.contains(filterPattern)) {
                matchedUsers.add(user);
            }
        }

        FilterResults results = new FilterResults();
        results.values = matchedUsers;

        return results;
    }

    @SuppressLint("NotifyDataSetChanged")
    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.filteredUserList.clear();
        adapter.filteredUserList.addAll((List<UserRequest>) results.values);
        adapter.notifyDataSetChanged(); // Notify the adapter for changes
    }
}
