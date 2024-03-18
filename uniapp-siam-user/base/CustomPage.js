import {themeChanged} from './behaviors/theme';
const CustomPage = function (options) {
    return Page(
        Object.assign({}, options, {
            behaviors: [themeMixin].concat(options.behaviors || []),
            onLoad(query) {
                const app = getApp();
                this.themeChanged(app.globalData.theme);
                if (app.globalData.watchThemeChange) {
                    app.globalData.watchThemeChange(this.themeChanged);
                }
                if (options.onLoad) {
                    options.onLoad.call(this, query);
                }
            },
            onUnload() {
                const app = getApp();
                if (app.globalData.unWatchThemeChange) {
                    app.globalData.unWatchThemeChange(this.themeChanged);
                }
                if (options.onUnload) {
                    options.onUnload.call(this);
                }
            }
        })
    );
};
export default CustomPage;
