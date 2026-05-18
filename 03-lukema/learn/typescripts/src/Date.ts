

const now = new Date();
const date1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
const date2 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate());

console.log('  now', now, now.getFullYear(), now.getMonth(), now.getDate());
console.log('date1', date1, date1.getFullYear(), date1.getMonth(), date1.getDate());
console.log('date2', date2, date2.getFullYear(), date2.getMonth(), date2.getDate());

