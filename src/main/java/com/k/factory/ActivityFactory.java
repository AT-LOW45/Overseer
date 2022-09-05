package com.k.factory;

import com.k.modules.polly.Poll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;



@Configuration
public class ActivityFactory {
    @FunctionalInterface
    public interface PollArgs<A, Poll> {
        public com.k.modules.polly.Poll.PollBuilder apply(A pollB);
    }


//    @Bean
//    public PollArgs<Poll.PollBuilder, Poll> initPoll() {
//        return this::newPoll;
//    }

//    @Bean
//    public Poll newPoll(Poll.PollBuilder pollBuilder) {
//        return Poll.builder().build();
//    }
}
