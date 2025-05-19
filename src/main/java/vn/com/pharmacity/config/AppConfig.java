package vn.com.pharmacity.config;
//package vn.com.test.api.config;
//
//import javax.sql.DataSource;
//
//import org.quartz.JobDataMap;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
//
//import vn.com.test.core.service.impl.MovieStatusUpdaterJob;
//
//@Configuration
//@EnableScheduling
//@EnableAsync
//public class AppConfig {
//
//    @SuppressWarnings("unused")
//    private JobDetailFactoryBean movieStatusUpdaterJob;
//
//    @SuppressWarnings("unused")
//    private SimpleTriggerFactoryBean movieStatusUpdateTrigger;
//    
//    @Autowired
//    private DataSource dataSource;
//    
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource);
//    }
//    
//    @Bean
//    public JobDetailFactoryBean movieStatusUpdaterJob() {
//        JobDetailFactoryBean factory = new JobDetailFactoryBean();
//        factory.setJobClass(MovieStatusUpdaterJob.class);
//        factory.setDurability(true);
//        
//        // Gán giá trị cho biến movieStatusUpdaterJob
//        movieStatusUpdaterJob = factory;
//        
//        return factory;
//    }
//
//
//    @Bean
//    public SimpleTriggerFactoryBean movieStatusUpdateTrigger() {
//        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
//        factory.setJobDetail(movieStatusUpdaterJob().getObject());
//        factory.setStartDelay(0); // Chạy ngay sau khi khởi động ứng dụng
//        factory.setRepeatInterval(60000); // Tần suất chạy sau mỗi 1 phút (60 giây)
//        
//        // Gán giá trị cho biến movieStatusUpdateTrigger
//        movieStatusUpdateTrigger = factory;
//        
//        return factory;
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(@Autowired JobDetailFactoryBean movieStatusUpdaterJob,
//            @Autowired SimpleTriggerFactoryBean movieStatusUpdateTrigger) throws SchedulerException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//
//        // Thiết lập các job và trigger
//        factory.setJobDetails(movieStatusUpdaterJob.getObject());
//        factory.setTriggers(movieStatusUpdateTrigger.getObject());
//
//        // Khởi động Scheduler
//        factory.setAutoStartup(true);
//        factory.setApplicationContextSchedulerContextKey("applicationContext");
//
//        // Lấy đối tượng JobDetail từ movieStatusUpdaterJob
//        JobDetail jobDetail = movieStatusUpdaterJob.getObject();
//
//        // Đặt giá trị cho param1 và param2 trong JobDataMap
//        JobDataMap jobDataMap = jobDetail.getJobDataMap();
//        jobDataMap.put("param1", "value1");
//        jobDataMap.put("param2", 123);
//
//        // Bắt đầu scheduler
//        factory.start();
//
//        return factory;
//    }
//
//}
//
