package edu.ucsd.billjyc.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;

import edu.ucsd.billjyc.minilinkedin.model.Experience;
import edu.ucsd.billjyc.minilinkedin.util.DateUtils;

public class ExperienceEditActivity extends EditBaseActivity<Experience> {
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";


    @Override
    protected void saveAndExit(@NonNull Experience data) {
        if(data == null) {
            data = new Experience();
        }

        data.company = ((EditText) findViewById(R.id.experience_edit_company)).getText().toString();
        data.title = ((EditText) findViewById(R.id.experience_edit_title)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.experience_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.experience_edit_end_date)).getText().toString());
        data.details = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.experience_edit_details)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXPERIENCE, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void setUpForCreate() {
        findViewById(R.id.experience_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setUpForEdit(@NonNull final Experience data) {
        ((EditText)findViewById(R.id.experience_edit_title)).setText(data.title);
        ((EditText)findViewById(R.id.experience_edit_company)).setText(data.company);
        ((EditText)findViewById(R.id.experience_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((EditText)findViewById(R.id.experience_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((EditText)findViewById(R.id.experience_edit_details)).setText(TextUtils.join("\n", data.details));

        findViewById(R.id.experience_edit_delete).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EXPERIENCE_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected Experience initializeData() {
        return getIntent().getParcelableExtra(KEY_EXPERIENCE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_experience_edit;
    }
}
