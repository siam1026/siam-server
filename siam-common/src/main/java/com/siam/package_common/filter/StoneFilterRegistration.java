package com.siam.package_common.filter;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoneFilterRegistration {

    private final StoneFilter filter;

    private final List<String> includePatterns = new ArrayList();

    private final List<String> excludePatterns = new ArrayList();

    @Nullable
    private PathMatcher pathMatcher;

    public StoneFilterRegistration(StoneFilter filter) {
        Assert.notNull(filter, "Interceptor is required");
        this.filter = filter;
    }

    public StoneFilterRegistration addPathPatterns(String... patterns) {
        return this.addPathPatterns(Arrays.asList(patterns));
    }

    public StoneFilterRegistration addPathPatterns(List<String> patterns) {
        this.includePatterns.addAll(patterns);
        return this;
    }

    public StoneFilterRegistration excludePathPatterns(String... patterns) {
        return this.excludePathPatterns(Arrays.asList(patterns));
    }

    public StoneFilterRegistration excludePathPatterns(List<String> patterns) {
        this.excludePatterns.addAll(patterns);
        return this;
    }

    public StoneFilterRegistration pathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        return this;
    }

    public Object getFilter() {
        if (this.includePatterns.isEmpty() && this.excludePatterns.isEmpty()) {
            return this.filter;
        } else {
            String[] include = StringUtils.toStringArray(this.includePatterns);
            String[] exclude = StringUtils.toStringArray(this.excludePatterns);
            StoneMappedFilter stoneMappedFilter = new StoneMappedFilter(include, exclude);
            if (this.pathMatcher != null) {
                stoneMappedFilter.setPathMatcher(this.pathMatcher);
            }

            return stoneMappedFilter;
        }
    }

    public String getFilterClassName() {
        return filter.getClass().getName();
    }
}