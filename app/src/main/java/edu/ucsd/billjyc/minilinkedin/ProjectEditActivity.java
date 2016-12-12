package edu.ucsd.billjyc.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;

import edu.ucsd.billjyc.minilinkedin.model.Project;
import edu.ucsd.billjyc.minilinkedin.util.DateUtils;

public class ProjectEditActivity extends EditBaseActivity<Project> {
    public static final String KEY_PROJECT = "project";

    @Override
    protected void saveAndExit(@NonNull Project data) {
        if(data == null) {
            data = new Project();
        }
        data.name = ((EditText)findViewById(R.id.project_edit_title)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText)findViewById(R.id.project_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText)findViewById(R.id.project_edit_end_date)).getText().toString());
        data.details = Arrays.asList(TextUtils.split(
                ((EditText)findViewById(R.id.project_edit_details)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_PROJECT, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void setUpForCreate() {
        findViewById(R.id.project_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setUpForEdit(@NonNull Project data) {
        ((EditText)findViewById(R.id.project_edit_title)).setText(data.name);
        ((EditText)findViewById(R.id.project_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((EditText)findViewById(R.id.project_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((EditText)findViewById(R.id.project_edit_details))
                .setText(TextUtils.join("\n", data.details));
    }

    @Override
    protected Project initializeData() {
        return getIntent().getParcelableExtra(KEY_PROJECT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_edit;
    }
}
