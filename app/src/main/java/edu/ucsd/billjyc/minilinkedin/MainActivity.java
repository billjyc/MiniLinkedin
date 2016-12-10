package edu.ucsd.billjyc.minilinkedin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.billjyc.minilinkedin.model.BasicInfo;
import edu.ucsd.billjyc.minilinkedin.model.Education;
import edu.ucsd.billjyc.minilinkedin.util.DateUtils;

public class MainActivity extends AppCompatActivity {

    private BasicInfo basicInfo;
    private Education education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fakeData();
        setUpUI();
    }

    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "Jing Guo";
        basicInfo.email = "guojing@jiuzhang.com";

        education = new Education();
        education.school = "UCSD";
        education.major = "Computer Science";
        education.startDate = DateUtils.stringToDate("09/2015");
        education.endDate = DateUtils.stringToDate("03/2017");
        education.courses = new ArrayList<>();
        education.courses.add("Database");
        education.courses.add("Algorithms");
        education.courses.add("Networks");
    }

    private void setUpUI() {
        setContentView(R.layout.activity_main);

        setUpBasicInfoUI();
        setUpEducationUI();
    }

    private void setUpBasicInfoUI() {
        ((TextView)findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView)findViewById(R.id.email)).setText(basicInfo.email);
    }

    private void setUpEducationUI() {
        ((TextView)findViewById(R.id.education_school)).setText(
                education.school + "(" + DateUtils.dateToString(education.startDate) + " ~ " +
                        DateUtils.dateToString(education.endDate)
        );
        ((TextView)findViewById(R.id.education_courses)).setText(formatItems(education.courses));
    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
