export default class StringSerice {
    static trim(str) {
        if (str === null || str === undefined) {
            return str;
        }
        if (String.prototype.trim) {
            return str.trim();
        } else {
            return str.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
        }
    }
    static isEmpty(str) {
        if (str === null || str === undefined) {
            return true;
        }
        if (this.trim(str) == '') {
            return true;
        }
        return false;
    }
    static containsSubStr(str, substr) {
        if (!str || !substr) {
            return false;
        }
        substr = this.trim(substr);
        if (str && str.toUpperCase().includes(substr.toUpperCase() || null)) {
            return true;
        } else {
            return false;
        }
    }
    static strEqual(a, b, ignoreCase) {
        if (!a) {
            a = '';
        }
        if (!b) {
            b = '';
        }
        a = this.trim(a);
        b = this.trim(b);
        if (ignoreCase) {
            a = a.toUpperCase();
            b = b.toUpperCase();
        }
        return a === b;
    }
}
