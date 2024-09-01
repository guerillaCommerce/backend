//package com.github.commerce.config.batch;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class UpdateCustomerGradeScheduler { //매월 1일 오전 12:00:00 구매 금액에 따른 회원 등급 조정
//
////    @Autowired
////    private JobLauncher jobLauncher; //Scheduling할 때 따로 AutoWired 해주어야함.
////
////    @Autowired
////    private Job updateCustomerGradeJob;
////
////    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul") //초 분 시 일 월 요일 (*: 매번) - 매월 1일 오전 12:00:00 구매 금액에 따른 회원 등급 조정
////    public void updateCustomerGradeJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
////
////        JobParameters jobParameters = new JobParameters(
////                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
////        );
////
////        jobLauncher.run(updateCustomerGradeJob, jobParameters);
////    }
//}
