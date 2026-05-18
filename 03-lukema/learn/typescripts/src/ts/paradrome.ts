
const strs: string[] = ['yes', 'ama h AMA', null, undefined, ''];

function isParadorme(str: string): boolean {
    if (typeof str == undefined || str == null) {
        return false;
    }

    str = str.toLowerCase();

    const len = str.length;

    for (let i = 0; i < len / 2; i++) {
        if (str.charAt(i) !== str.charAt(len - i - 1)) {
            return false;
        }
    }

    return true;
}

strs.forEach(item => {
    console.log(item, isParadorme(item));
})

strs.filter(item => isParadorme(item)).forEach(console.log);
strs.filter(item => !isParadorme(item)).forEach(console.log);



