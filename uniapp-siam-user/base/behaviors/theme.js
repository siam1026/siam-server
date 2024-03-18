module.exports = {
    data() {
        return {
            theme: 'light'
        };
    },
    methods: {
        themeChanged(theme) {
            this.setData({
                theme
            });
        }
    }
};
