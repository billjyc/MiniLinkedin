package edu.ucsd.billjyc.minilinkedin.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by billjyc on 2016/12/10.
 */

public class Project {
    public String id;

    public String name;

    public Date startDate;

    public Date endDate;

    public List<String> details;

    public Project() {
        id = UUID.randomUUID().toString();
    }

}

