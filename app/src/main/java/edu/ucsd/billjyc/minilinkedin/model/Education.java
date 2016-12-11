package edu.ucsd.billjyc.minilinkedin.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by billjyc on 2016/12/10.
 */

public class Education {
    public String id;

    public String school;

    public String major;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education() {
        id = UUID.randomUUID().toString();
    }
}
