package pl.mazi.ledapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.*;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.model.PatternModel;

public class MyDialog extends Dialog {

    private Button btn_confirm;
    private Button btn_cancel;

    private TextView tv_title;
    private PatternModel patternModel;
    private LinearLayout layout;

    public MyDialog(@NonNull @NotNull Context context, PatternModel patternModel) {
        super(context);
        this.patternModel = patternModel;

        setContentView(R.layout.popup_pattern);
        initVariables();
        setOnClick();
        showAndWait();
    }

    private void showAndWait() {


        // TESTING
        switch (patternModel.getType()){
            case SINGLE:
                TextView textView = new TextView(getContext());
                textView.setText("Test");
                layout.addView(textView);
                break;

            case CUSTOM:
                RadioGroup radioGroup = new RadioGroup(getContext());
                for(String item : patternModel.getPatternOptions()){
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(item);
                    radioGroup.addView(radioButton);
                }
                layout.addView(radioGroup);
                break;
        }

        tv_title.setText(patternModel.getTitle());
        show();
    }

    private void initVariables() {
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_confirm = findViewById(R.id.btn_confirm);
        layout = findViewById(R.id.layout_Options);
        tv_title = findViewById(R.id.tv_title);
    }

    private void setOnClick(){
        btn_confirm.setOnClickListener(e->{
            dismiss();
        });

        btn_cancel.setOnClickListener(e->{
            dismiss();
        });
    }
}
