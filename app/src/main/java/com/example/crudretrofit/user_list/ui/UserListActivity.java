package com.example.crudretrofit.user_list.ui;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudretrofit.R;
import com.example.crudretrofit.adapter.UserAdapter;
import com.example.crudretrofit.user_list.userlist_models.GetAllUsersResponse;
import com.example.crudretrofit.user.user_models.UserRequest;
import com.example.crudretrofit.retrofit.UserRepository;

import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView userRv;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_list);
        setupWindowInsets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeViews();
        openSearchView();
        fetchUsers();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        userRv = findViewById(R.id.userRv);
        searchView = findViewById(R.id.searchView);
        CardView cardViewSearch = findViewById(R.id.cardViewSearch);
        cardViewSearch.setOnClickListener(v -> openSearchView());
    }

    private void openSearchView() {
        searchView.setIconified(false);
        searchView.requestFocus();
    }

    private void fetchUsers() {
        UserRepository userRepository=new UserRepository();
        userRepository.getAllUsers(new UserRepository.UserCallback<GetAllUsersResponse>() {
            @Override
            public void onSuccess(GetAllUsersResponse data) {
                setupRecyclerView(data.getData());
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void setupRecyclerView(List<UserRequest> userList) {
        if (userList != null) {
            UserAdapter adapter = new UserAdapter(this, userList, R.color.white);
            userRv.setLayoutManager(new LinearLayoutManager(this));
            userRv.setHasFixedSize(true);
            userRv.setAdapter(adapter);
            adapter.setOnDeleteClickListener(position -> adapter.removeUser(position));
            setupSearchListener(adapter);
        }
    }

    private void setupSearchListener(UserAdapter adapter) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
