package com.siam.package_common.filter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StoneFilterRegistry {

    private final List<StoneFilterRegistration> registrations = new ArrayList();

    public StoneFilterRegistry() {
    }

    public StoneFilterRegistration addFilter(StoneFilter filter) {
        StoneFilterRegistration registration = new StoneFilterRegistration(filter);
        this.registrations.add(registration);
        return registration;
    }

    public StoneFilterRegistration getFilter(String className) {
        for (StoneFilterRegistration stoneFilterRegistration : registrations) {
            if(stoneFilterRegistration.getFilterClassName().equals(className)){
                return stoneFilterRegistration;
            }
        }
        return null;
    }
}