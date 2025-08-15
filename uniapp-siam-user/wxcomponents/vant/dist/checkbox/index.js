import { useParent } from '../common/relation';
import { VantComponent } from '../common/component';
function emit(target, value) {
    target.$emit('input', value);
    target.$emit('change', value);
}
VantComponent({
    field: true,
    relation: useParent('checkbox-group'),
    classes: ['icon-class', 'label-class'],
    props: {
        value: Boolean,
        disabled: Boolean,
        useIconSlot: Boolean,
        checkedColor: String,
        labelPosition: {
            type: String,
            value: 'right',
        },
        labelDisabled: Boolean,
        shape: {
            type: String,
            value: 'round',
        },
        iconSize: {
            type: null,
            value: 20,
        },
    },
    data: {
        parentDisabled: false,
        direction: 'vertical',
    },
    methods: {
        emitChange(value) {
            if (this.parent) {
                this.setParentValue(this.parent, value);
            }
            else {
                emit(this, value);
            }
        },
        toggle() {
            const { parentDisabled, disabled, value } = this.data;
            if (!disabled && !parentDisabled) {
                this.emitChange(!value);
            }
        },
        onClickLabel() {
            const { labelDisabled, parentDisabled, disabled, value } = this.data;
            if (!disabled && !labelDisabled && !parentDisabled) {
                this.emitChange(!value);
            }
        },
        setParentValue(parent, value) {
            const parentValue = parent.data.value.slice();
            const { name } = this.data;
            const { max } = parent.data;
            if (value) {
                if (max && parentValue.length >= max) {
                    return;
                }
                if (parentValue.indexOf(name) === -1) {
                    parentValue.push(name);
                    emit(parent, parentValue);
                }
            }
            else {
                const index = parentValue.indexOf(name);
                if (index !== -1) {
                    parentValue.splice(index, 1);
                    emit(parent, parentValue);
                }
            }
        },
    },
});
