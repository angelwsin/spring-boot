package com.spring.boot.start;


import com.spring.boot.start.job.Job;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

@Component
public class Scheduled implements ApplicationContextAware,InitializingBean{



    private ApplicationContext applicationContext;



    private  final  ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();





    public void addTask(String beanName){
        Job job = this.applicationContext.getBean(beanName, Job.class);
        try {
            Method exeMethod = Job.class.getMethod("exe");
            // scheduledTaskRegistrar.addCronTask(createRunnable(job,exeMethod),"0/3 * * * * ?");
            scheduledTaskRegistrar.scheduleTriggerTask(new TriggerTask(createRunnable(job,exeMethod),new CronTrigger("0/3 * * * * ?")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected Runnable createRunnable(Object target, Method method) {
        Assert.isTrue(method.getParameterCount() == 0, "Only no-arg methods may be annotated with @Scheduled");
        Method invocableMethod = AopUtils.selectInvocableMethod(method, target.getClass());
        return new ScheduledMethodRunnable(target, invocableMethod);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledTaskRegistrar.afterPropertiesSet();
    }
}
