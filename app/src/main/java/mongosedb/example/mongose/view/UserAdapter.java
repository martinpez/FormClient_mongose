package mongosedb.example.mongose.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mongosedb.example.mongose.R;
import mongosedb.example.mongose.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private OnUserActionListener listener;


    // Constructor

    public UserAdapter(List<User> userList,  Context context, OnUserActionListener listener) {
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }
    // Interfaz para manejar las acciones de usuario
    public interface OnUserActionListener {
        void onUpdate(String email);
        void onDelete(String email);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position ) {
        User user = userList.get(position);
        holder.text_name.setText(user.getName());
        holder.text_email.setText(user.getEmail());
        holder.itemView.findViewById(R.id.btn_update).setOnClickListener(v -> listener.onUpdate(user.getEmail()));
        holder.itemView.findViewById(R.id.btn_delete).setOnClickListener(v -> listener.onDelete(user.getEmail()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView text_name , text_email;
        Button btn_update, btn_delete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            text_email = itemView.findViewById(R.id.text_email);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

