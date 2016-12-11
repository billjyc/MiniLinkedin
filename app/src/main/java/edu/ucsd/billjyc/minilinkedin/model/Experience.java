package edu.ucsd.billjyc.minilinkedin.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by billjyc on 2016/12/10.
 */

public class Experience {
    public String id;

    public String company;

    public String title;

    public Date startDate;

    public Date endDate;

    public List<String> details;

    public Experience() {
        id = UUID.randomUUID().toString();
    }
}
