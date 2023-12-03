package com.siam.package_common.filter;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;

public final class StoneMappedFilter implements StoneFilter {
    @Nullable
    private final String[] includePatterns;

    @Nullable
    private final String[] excludePatterns;

    @Nullable
    private PathMatcher pathMatcher;

    public StoneMappedFilter(@Nullable String[] includePatterns, @Nullable String[] excludePatterns) {
        this.includePatterns = includePatterns;
        this.excludePatterns = excludePatterns;
    }

    public void setPathMatcher(@Nullable PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    @Nullable
    public PathMatcher getPathMatcher() {
        return this.pathMatcher;
    }

    @Nullable
    public String[] getPathPatterns() {
        return this.includePatterns;
    }

    public boolean matches(String lookupPath, PathMatcher pathMatcher) {
        PathMatcher pathMatcherToUse = this.pathMatcher != null ? this.pathMatcher : pathMatcher;
        String[] var4;
        int var5;
        int var6;
        String pattern;
        if (!ObjectUtils.isEmpty(this.excludePatterns)) {
            var4 = this.excludePatterns;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
                pattern = var4[var6];
                if (pathMatcherToUse.match(pattern, lookupPath)) {
                    return false;
                }
            }
        }

        //这里我要做一下改动，如果includePatterns为空，则不拦截任何请求，返回false
        if (ObjectUtils.isEmpty(this.includePatterns)) {
            return false;
        } else {
            var4 = this.includePatterns;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
                pattern = var4[var6];
                if (pathMatcherToUse.match(pattern, lookupPath)) {
                    return true;
                }
            }

            return false;
        }
    }
}