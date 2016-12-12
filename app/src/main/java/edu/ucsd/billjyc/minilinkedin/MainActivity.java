package edu.ucsd.billjyc.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.billjyc.minilinkedin.model.BasicInfo;
import edu.ucsd.billjyc.minilinkedin.model.Education;
import edu.ucsd.billjyc.minilinkedin.model.Experience;
import edu.ucsd.billjyc.minilinkedin.model.Project;
import edu.ucsd.billjyc.minilinkedin.util.DateUtils;
import edu.ucsd.billjyc.minilinkedin.util.ImageUtils;
import edu.ucsd.billjyc.minilinkedin.util.ModelUtils;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_EXPERIENCE_EDIT = 101;
    private static final int REQ_CODE_PROJECT_EDIT = 102;
    private static final int REQ_CODE_BASIC_INFO_EDIT = 103;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fakeData();
        loadData();
        setUpUI();
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read(this, MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read(this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read(this,
                MODEL_EXPERIENCES,
                new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;
        List<Project> savedProjects = ModelUtils.read(this,
                MODEL_PROJECTS,
                new TypeToken<List<Project>>(){});
        projects = savedProjects == null ? new ArrayList<Project>() : savedProjects;
    }

    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "Jing Guo";
        basicInfo.email = "guojing@jiuzhang.com";

        Education education = new Education();
        education.school = "UCSD";
        education.major = "Computer Science";
        education.startDate = DateUtils.stringToDate("09/2015");
        education.endDate = DateUtils.stringToDate("03/2017");
        education.courses = new ArrayList<>();
        education.courses.add("Database");
        education.courses.add("Algorithms");
        education.courses.add("Networks");

        Education education2 = new Education();
        education2.school = "Nanjing University";
        education2.major = "Software Engineering";
        education2.startDate = DateUtils.stringToDate("09/2011");
        education2.endDate = DateUtils.stringToDate("06/2015");
        education2.courses = new ArrayList<>();
        education2.courses.add("Database");
        education2.courses.add("Algorithms");
        education2.courses.add("Networks");

        educations = new ArrayList<>();
        educations.add(education);
        educations.add(education2);

        Experience experience1 = new Experience();
        experience1.title = "Social Recycle";
        experience1.company = "LinkedIn";
        experience1.startDate = DateUtils.stringToDate("09/2016");
        experience1.endDate = DateUtils.stringToDate("06/2018");
        experience1.details = new ArrayList<>();
        experience1.details.add("ruby on rails");
        experience1.details.add("networks");

        experiences = new ArrayList<>();
        experiences.add(experience1);

        Project project1 = new Project();
        project1.name = "Social Recycle";
        project1.startDate = DateUtils.stringToDate("09/2015");
        project1.endDate = DateUtils.stringToDate("11/2015");
        project1.details = new ArrayList<>();
        project1.details.add("Ruby on rails");
        project1.details.add("sqllite");

        projects = new ArrayList<>();
        projects.add(project1);

    }

    private void setUpUI() {
        setContentView(R.layout.activity_main);

        ImageButton editBasicInfoBtn = (ImageButton)findViewById(R.id.edit_basic_info);

        ImageButton addEducationBtn = (ImageButton)findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        ImageButton addExperienceBtn = (ImageButton)findViewById(R.id.add_experience_btn);
        addExperienceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        ImageButton addProjectBtn = (ImageButton)findViewById(R.id.add_project_btn);
        addProjectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });


        setUpBasicInfo();
        setUpEducations();
        setUpExperiences();
        setUpProjects();
    }



    private void setUpBasicInfo() {
        ((TextView)findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)
            ? "Your name" : basicInfo.name);
        ((TextView)findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)
            ? "Your email" : basicInfo.email);

        ImageView userPicture = (ImageView)findViewById(R.id.user_picture);
        if(basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        } else {
            userPicture.setImageResource(R.drawable.user_ghost);
        }

        findViewById(R.id.edit_basic_info).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASIC_INFO_EDIT);
            }
        });

    }

    private void setUpEducations() {
        LinearLayout educationsLayout = (LinearLayout)findViewById(R.id.educations);

        educationsLayout.removeAllViews();
        for(Education education : educations) {
            educationsLayout.addView(getEducationView(education));
        }
    }

    private View getEducationView(@NonNull final Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item, null);
        String dateString = DateUtils.dateToString(education.startDate) + " ~ " + DateUtils.dateToString(education.endDate);

        ((TextView)view.findViewById(R.id.education_school)).setText(education.school + "(" + dateString + ")");
        ((TextView)view.findViewById(R.id.education_courses)).setText(formatItems(education.courses));

        ImageButton editEducationBtn = (ImageButton)view.findViewById(R.id.edit_education_btn);
        editEducationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
        return view;
    }

    private void setUpExperiences() {
        LinearLayout experiencesLayout = (LinearLayout)findViewById(R.id.experiences);

        experiencesLayout.removeAllViews();
        for(Experience experience : experiences) {
            experiencesLayout.addView(getExperienceView(experience));
        }
    }

    private View getExperienceView(final Experience experience) {
        View view = getLayoutInflater().inflate(R.layout.experience_item, null);
        String dateString = DateUtils.dateToString(experience.startDate) + " ~ " + DateUtils.dateToString(experience.endDate);

        ((TextView)view.findViewById(R.id.experience_company)).setText(experience.company + "(" + dateString + ")");
        ((TextView)view.findViewById(R.id.experience_details)).setText(formatItems(experience.details));

        ImageButton editExperienceBtn = (ImageButton)view.findViewById(R.id.edit_experience_btn);
        editExperienceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        return view;
    }

    private void setUpProjects() {
        LinearLayout projectsLayout = (LinearLayout)findViewById(R.id.projects);

        projectsLayout.removeAllViews();
        for(Project project : projects) {
            projectsLayout.addView(getProjectView(project));
        }
    }

    private View getProjectView(final Project project) {
        View view = getLayoutInflater().inflate(R.layout.project_item, null);
        String dateString = DateUtils.dateToString(project.startDate) + " ~ " + DateUtils.dateToString(project.endDate);

        ((TextView)view.findViewById(R.id.project_name)).setText(project.name + "(" + dateString + ")");
        ((TextView)view.findViewById(R.id.project_details)).setText(formatItems(project.details));

        ImageButton editProjectBtn = (ImageButton)view.findViewById(R.id.edit_project_btn);
        editProjectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        return view;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDUCATION_EDIT:
                    String educationId = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if(educationId != null) {
                        deleteEducation(educationId);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        Log.d("receive data", "receive education");
                        updateEducation(education);
                        setUpEducations();
                    }
                    break;
                case REQ_CODE_EXPERIENCE_EDIT:
                    String experienceId = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if(experienceId != null) {
                        deleteExperience(experienceId);
                    } else {
                        Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_PROJECT_EDIT:
                    String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if(projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
                case REQ_CODE_BASIC_INFO_EDIT:
                    BasicInfo basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;
            }
        }


    }

    private void deleteProject(String projectId) {
        for (int i = 0; i < projects.size(); ++i) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, projectId)) {
                projects.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setUpProjects();
    }

    private void deleteExperience(String experienceId) {
        for (int i = 0; i < experiences.size(); ++i) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experienceId)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setUpExperiences();
    }

    private void deleteEducation(String educationId) {
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, educationId)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setUpEducations();
    }

    private void updateBasicInfo(BasicInfo basicInfo) {
        ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);

        this.basicInfo = basicInfo;
        setUpBasicInfo();
    }

    private void updateProject(Project project) {
        boolean found = false;
        for(int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            if(TextUtils.equals(p.id, project.id)) {
                found = true;
                projects.set(i, project);
                break;
            }
        }
        if(!found) {
            projects.add(project);
        }
        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setUpProjects();
    }

    private void updateExperience(Experience experience) {
        boolean found = false;
        for(int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            if(TextUtils.equals(e.id, experience.id)) {
                found = true;
                experiences.set(i, experience);
                break;
            }
        }
        if(!found) {
            experiences.add(experience);
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setUpExperiences();
    }

    private void updateEducation(Education education) {
        boolean found = false;
        for(int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if(TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }

        if(!found) {
            educations.add(education);
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setUpEducations();
    }
}
