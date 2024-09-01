//package com.github.commerce.config.batch;
//
//import com.github.commerce.repository.coupon.UsersCouponRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//@EnableBatchProcessing //Spring Batch
//@RequiredArgsConstructor
//public class ExpiredUsersCouponJobConfig {
//
//    private final UsersCouponRepository usersCouponRepository;
//
////    @Autowired
////    private JobBuilderFactory jobBuilderFactory;
////
////    @Autowired
////    private StepBuilderFactory stepBuilderFactory;
////
////    @Bean
////    public Job expiredUsersCouponJob(Step expiredUsersCouponStep){
////        return jobBuilderFactory.get("expiredUsersCouponJob")
////                .incrementer(new RunIdIncrementer())
////                .start(expiredUsersCouponStep())
////                .build();
////    }
////
////    //Job 하위 Step 1개
////    @JobScope
////    @Bean
////    public Step expiredUsersCouponStep(){
////        return stepBuilderFactory.get("expiredUsersCouponStep")
////                .tasklet(expiredUsersCouponTasklet())
////                .build();
////    }
////
////    //Step 하위 Tasklet 1개
////    @StepScope
////    @Bean
////    public Tasklet expiredUsersCouponTasklet(){
////        return new Tasklet() {
////            @Override
////            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
////                //log.info("쿠폰 삭제 시작");
////                usersCouponRepository.deleteUsersCouponByExpiredAtBefore(LocalDateTime.now());
////                //log.info("쿠폰 삭제 종료");
////                return RepeatStatus.FINISHED;
////            }
////        };
////    }
//}
