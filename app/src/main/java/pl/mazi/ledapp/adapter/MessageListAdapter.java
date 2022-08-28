package pl.mazi.ledapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.model.MessageInfoModel;
import pl.mazi.ledapp.model.PatternModel;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////

    // Message list
    private ArrayList<MessageInfoModel> messageList;

    // Context
    private Context context;

    /////////////////////////////////////////////////////////////////
    //// VIEW HOLDER CLASS
    /////////////////////////////////////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_message;
        private TextView tv_messageDate;
        private LinearLayout messageItemLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_message = itemView.findViewById(R.id.messageTextView);
            tv_messageDate = itemView.findViewById(R.id.dateTextView);
            messageItemLayout = itemView.findViewById(R.id.linearLayoutMessage);
        }
    }

    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTOR
    /////////////////////////////////////////////////////////////////
    public MessageListAdapter(ArrayList<MessageInfoModel> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    public MessageListAdapter(Context context) {
        this.context = context;
        this.messageList = new ArrayList<>();
    }

    /////////////////////////////////////////////////////////////////
    //// OVERRIDE METHODS
    /////////////////////////////////////////////////////////////////
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create view with message item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_info_layout,parent,false);

        // Return new view holder with created view
        return new ViewHolder(view);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        // Get item view holder
        ViewHolder itemHolder = (ViewHolder) holder;

        // Get message from list
        final MessageInfoModel messageInfoModel = (MessageInfoModel) messageList.get(position);

        // Create new params to layout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
        // Set margins to layout in item
        params.setMargins(10,10,10,10);

        // Set gravity message
        switch(messageInfoModel.getMessageTyp()){

            // Set the message to the middle in the layout
            case MESSAGE_ERROR:
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                itemHolder.messageItemLayout.setBackgroundColor(Color.parseColor("#20eb3434"));
                itemHolder.messageItemLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                break;

            // Set the message to the right in the layout
            case MESSAGE_SENDER:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                itemHolder.messageItemLayout.setBackgroundColor(Color.parseColor("#2034d5eb"));
                itemHolder.messageItemLayout.setGravity(Gravity.END);
                break;

            // Set the message to the left in the layout
            case MESSAGE_RECEIVER:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                itemHolder.messageItemLayout.setBackgroundColor(Color.parseColor("#20ff007b"));
                itemHolder.messageItemLayout.setGravity(Gravity.START);
                break;
        }

        // Set params to item layout
        itemHolder.messageItemLayout.setLayoutParams(params);

        // Set data into item message
        itemHolder.tv_message.setText(messageInfoModel.getMessage());
        itemHolder.tv_messageDate.setText(messageInfoModel.getMessageTime());
    }

    @Override
    public int getItemCount() {
        // Return the number of messages
        return messageList.size();
    }

    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    @SuppressLint("NotifyDataSetChanged")
    public void addNewMessage(String message, MessageInfoModel.MessageTyp messageTyp){
        // Add new message
        this.messageList.add(new MessageInfoModel(message,messageTyp));

        // Refresh data
        notifyDataSetChanged();
    }
}
