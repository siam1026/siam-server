import { link } from '../mixins/link';
import { VantComponent } from '../common/component';
VantComponent({
    classes: [
        'num-class',
        'desc-class',
        'thumb-class',
        'title-class',
        'price-class',
        'origin-price-class',
    ],
    mixins: [link],
    props: {
        tag: String,
        num: String,
        desc: String,
        thumb: String,
        title: String,
        price: {
            type: String,
            observer: 'updatePrice',
        },
        centered: Boolean,
        lazyLoad: Boolean,
        thumbLink: String,
        originPrice: String,
        thumbMode: {
            type: String,
            value: 'aspectFit',
        },
        currency: {
            type: String,
            value: '¥',
        },
    },
    methods: {
        updatePrice() {
            const { price } = this.data;
            const priceArr = price.toString().split('.');
            this.setData({
                integerStr: priceArr[0],
                decimalStr: priceArr[1] ? `.${priceArr[1]}` : '',
            });
        },
        onClickThumb() {
            this.jumpLink('thumbLink');
        },
    },
});
