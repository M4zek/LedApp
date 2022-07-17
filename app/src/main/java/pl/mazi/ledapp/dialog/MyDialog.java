package pl.mazi.ledapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.*;
import androidx.annotation.NonNull;

import pl.mazi.ledapp.R;
import pl.mazi.ledapp.fragment.MessageFragment;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.PatternModel;
import top.defaults.colorpicker.ColorPickerView;


public class MyDialog extends Dialog {

    private PatternModel patternModel;
    private TextView tv_title;
    private TextView tv_description;
    private LinearLayout optionLayout;
    private Button button_Confirm;
    private Button button_Cancel;

    public MyDialog(@NonNull Context context, Builder builder) {
        super(context);
        setContentView(R.layout.pattern_item_popup);
        this.patternModel = builder.patternModel;
        initMethods();
    }


    public static class Builder{
        private Context context;
        private PatternModel patternModel;

        public Builder(Context context){
            this.context = context;
        }

        public Builder pattern(PatternModel pModel){
            patternModel = pModel;
            return this;
        }
        public MyDialog build(){ return new MyDialog(context, this); }
    }


    /////////////////////////////////////////////////////////////
    private void initMethods() {
        initVariables();
        initDialogView();
    }

    private void initDialogView() {
        switch (patternModel.getStyle()){
            case COLOR:
                ColorPickerView picker = createColorPicker();
                optionLayout.addView(picker);
                button_Confirm.setOnClickListener(event->{
                    int color = picker.getColor();
                    MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,"You picked: " + color).sendToTarget();
                });
                break;

            case BOX:
                RadioGroup radioGroup = createRadioGroup();
                optionLayout.addView(radioGroup);
                button_Confirm.setOnClickListener(event->{
                    int id  = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(id);
                    MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,"You selected: " + radioButton.getText()).sendToTarget();
                });
                break;

            case SIMPLE:
                TextView textView = new TextView(getContext());
                textView.setText(R.string.pattern_approve);
                optionLayout.addView(textView);
                button_Confirm.setOnClickListener(event->{
                    MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,"You selected: " + patternModel.getTitle()).sendToTarget();
                });
                break;

        }
        tv_description.setText(patternModel.getDescription());
        tv_title.setText(patternModel.getTitle());
        initCancelClick();
    }


    private void initVariables() {
        tv_title = findViewById(R.id.tv_titlePattern);
        tv_description = findViewById(R.id.tv_descriptionPattern);
        optionLayout = findViewById(R.id.optionsLayout);
        button_Cancel = findViewById(R.id.btn_cancel);
        button_Confirm = findViewById(R.id.btn_confirm);
    }

    private void initCancelClick(){
        button_Cancel.setOnClickListener(event->{
            dismiss();
        });
    }

    private RadioGroup createRadioGroup(){
        RadioGroup radioGroup = new RadioGroup(getContext());
        for(String option : patternModel.getSubPatternList()){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
        return radioGroup;
    }


    private ColorPickerView createColorPicker() {
        ColorPickerView colorPickerView = new ColorPickerView(getContext());
        colorPickerView.setEnabledAlpha(false);
        colorPickerView.setInitialColor(Color.RED);
        colorPickerView.setEnabledBrightness(false);
        return colorPickerView;
    }
}
