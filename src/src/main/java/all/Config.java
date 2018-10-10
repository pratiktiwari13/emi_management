package all;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.task.TaskExecutor;
        import org.springframework.mail.javamail.JavaMailSender;
        import org.springframework.mail.javamail.JavaMailSenderImpl;
        import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

        import java.util.Properties;

@Configuration
public class Config {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        System.out.println("Bean1");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("CronJobTaskExecutor");
        executor.initialize();
        return executor;
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        System.out.println("Bean2");
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                CronJobRunnable cronJobRunnable = new CronJobRunnable();
                applicationContext.getAutowireCapableBeanFactory().autowireBean(cronJobRunnable);
                executor.execute(cronJobRunnable);
            }
        };
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        System.out.println("Bean3");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("pop.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("pratiktiwari13@gmail.com");
        mailSender.setPassword("dpndolfakufoqgiy");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        if(mailSender == null){
            System.out.println("Returning Null");
        }
        return mailSender;
    }
}