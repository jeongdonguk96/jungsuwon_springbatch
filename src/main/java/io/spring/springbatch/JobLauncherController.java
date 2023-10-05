//package io.spring.springbatch;
//
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@RequiredArgsConstructor
//public class JobLauncherController {
//
//    private final Job job;
//    private final JobLauncher jobLauncher;
//
//    @SneakyThrows
//    @PostMapping("/batch")
//    public String launch(@RequestBody Member member) {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("id", member.getId())
//                .addDate("date", new Date())
//                .toJobParameters();
//
//        jobLauncher.run(job, jobParameters);
//
//        return "batch completed";
//    }
//}
