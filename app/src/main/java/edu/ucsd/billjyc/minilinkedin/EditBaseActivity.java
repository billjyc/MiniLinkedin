package edu.ucsd.billjyc.minilinkedin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by billjyc on 2016/12/11.
 */

public abstract class EditBaseActivity<T> extends AppCompatActivity {
    private T data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = initializeData();
        if(data != null) {
            setUpForEdit(data);
        } else {
            setUpForCreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                Log.d("click button", "save button");
                saveAndExit(data);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void saveAndExit(@NonNull T data);

    protected abstract void setUpForCreate();

    protected abstract void setUpForEdit(@NonNull T data);

    protected abstract T initializeData();

    protected abstract int getLayoutId();
}
