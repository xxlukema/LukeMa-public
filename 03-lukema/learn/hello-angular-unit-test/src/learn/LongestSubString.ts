export const lengthOfLongestSubstring = (strs: string[]): string => {
  if ( !strs || strs.length === 0 ) {
    return '';
  }

  const sortedStrs = strs.slice().sort((a, b) => a.length - b.length);
  const primary = sortedStrs[0];
  const newArray = sortedStrs.slice(1);

  let longestSubstr = '';
  let maxLen = 0;
  let left = 0;
  let right = 1;

  while (left < primary.length && right <= primary.length) {
    const substr = primary.slice(left, right);
    const isFoundInEvery = newArray.every(str => str.includes(substr));
    if (isFoundInEvery) {
      longestSubstr = substr;
      maxLen = longestSubstr.length;
      right++;
    } else {
      left++;
      right = left + maxLen + 1;
    }
  }

  return longestSubstr;
}
