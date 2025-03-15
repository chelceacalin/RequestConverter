package com.requestConverter.RequestConverter.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class AppContext {

    private String username;
    private LocalDateTime localDateTime;

    private static final ThreadLocal<AppContext> appContext = ThreadLocal.withInitial(AppContext::new);

    public static AppContext getAppContext() {
        return appContext.get();
    }

    public static void setAppContext(AppContext context) {
        appContext.set(context);
    }

    public static void clear() {
        appContext.remove();
    }
}
