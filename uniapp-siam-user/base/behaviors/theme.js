export default {
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
