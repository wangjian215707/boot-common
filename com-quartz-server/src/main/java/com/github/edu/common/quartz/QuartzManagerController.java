package com.github.edu.common.quartz;

import com.github.edu.common.quartz.component.APIQuartzJob;
import com.github.edu.common.quartz.component.JDBCQuartzJob;
import com.github.edu.common.quartz.service.IQuartzServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuartzManagerController {

    @Autowired
    private IQuartzServerService serverService;

    @GetMapping("/s/add/test")
    public String add1(){
        serverService.addJob(JDBCQuartzJob.class, "job1", "test", "0/5 * * * * ?");
        return "1";
    }

    @GetMapping("/s/add/test1")
    public String add2(){
        serverService.addJob(APIQuartzJob.class, "job2", "test", "0/5 * * * * ?");
        return "2";
    }
}
