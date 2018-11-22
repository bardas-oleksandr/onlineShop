package ua.levelup.controller.support;

import org.springframework.stereotype.Component;

@Component("messageResolver")
public class MessageResolver {

    public String resolveMessageForException(Exception e){
        return "nothing_have_to_do_with_problem";
    }
}
