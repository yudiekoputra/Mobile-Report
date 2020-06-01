package com.pt.aiti.mobilereport.Utility;

import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class CustomExpandCollapseBar {
    private TextView textView;
    private ExpandableLinearLayout expandableLinearLayout;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private boolean toggle;

    public CustomExpandCollapseBar(TextView textView, ExpandableLinearLayout expandableLinearLayout, boolean toggle){
        this.textView = textView;
        this.expandableLinearLayout = expandableLinearLayout;
        this.toggle=toggle;

        init();
        setBehaviour();

    }

    public CustomExpandCollapseBar(TextView textView, ExpandableRelativeLayout expandableRelativeLayout, boolean toggle){
        this.textView = textView;
        this.expandableRelativeLayout = expandableRelativeLayout;
        this.toggle=toggle;

        init();
        setBehaviour();

    }

    private void init(){
        if(toggle){
            textView.setCompoundDrawablesWithIntrinsicBounds(0,0, android.R.drawable.arrow_up_float, 0);

            if(expandableLinearLayout != null){
                expandableLinearLayout.expand();
            }
            if (expandableRelativeLayout!=null){
                expandableRelativeLayout.expand();
            }
        }else {
            textView.setCompoundDrawablesWithIntrinsicBounds(0,0, android.R.drawable.arrow_down_float, 0);

            if(expandableLinearLayout != null){
                expandableLinearLayout.collapse();
            }
            if (expandableRelativeLayout!=null){
                expandableRelativeLayout.collapse();
            }
        }
        toggle= !toggle;
    }
    public void setBehaviour(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
    }
}
