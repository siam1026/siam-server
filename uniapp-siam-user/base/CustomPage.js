import theme from './behaviors/theme';
const CustomPage = function (options) {
    return Page(
        Object.assign({}, options, {
            behaviors: [themeMixin].concat(options.behaviors || []),
            onLoad(query) {
                let app = getApp();
                theme.themeChanged(app.globalData.theme);
                if (app.globalData.watchThemeChange) {
                    app.globalData.watchThemeChange(theme.themeChanged);
                }
                if (options.onLoad) {
                    options.onLoad.call(theme, query);
                }
            },
            onUnload() {
                let app = getApp();
                if (app.globalData.unWatchThemeChange) {
                    app.globalData.unWatchThemeChange(theme.themeChanged);
                }
                if (options.onUnload) {
                    options.onUnload.call(this);
                }
            }
        })
    );
};
export default CustomPage;
