package pl.coderslab.charity.donation.scheduler;

import org.quartz.Scheduler;
import org.quartz.impl.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class SchedulerJobService {
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    private SchedulerFactoryBean schedulerFactoryBean;
//
//    @Autowired
//    private SchedulerRepository schedulerRepository;
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Autowired
//    private JobScheduleCreator scheduleCreator;
}
