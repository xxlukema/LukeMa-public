import { lengthOfLongestSubstring } from './LongestSubString';

describe('lengthOfLongestSubstring', () => {

  describe('Edge Cases', () => {
    it('should return empty string when input is null', () => {
      const result = lengthOfLongestSubstring(null as any);
      expect(result).toBe('');
    });

    it('should return empty string when input is undefined', () => {
      const result = lengthOfLongestSubstring(undefined as any);
      expect(result).toBe('');
    });

    it('should return empty string when input array is empty', () => {
      const result = lengthOfLongestSubstring([]);
      expect(result).toBe('');
    });

    it('should return empty string when array contains only empty strings', () => {
      const result = lengthOfLongestSubstring(['', '', '']);
      expect(result).toBe('');
    });
  });

  describe('Single String Cases', () => {
    it('should return empty string when array contains one empty string', () => {
      const result = lengthOfLongestSubstring(['']);
      expect(result).toBe('');
    });

    it('should return the string itself when array contains one non-empty string', () => {
      const result = lengthOfLongestSubstring(['hello']);
      expect(result).toBe('hello');
    });
  });

  describe('Two String Cases', () => {
    it('should find common substring between two strings', () => {
      const result = lengthOfLongestSubstring(['hello', 'world']);
      expect(result).toBe('l');
    });

    it('should return empty string when no common substring exists', () => {
      const result = lengthOfLongestSubstring(['abc', 'def']);
      expect(result).toBe('');
    });

    it('should find longer common substring', () => {
      const result = lengthOfLongestSubstring(['testing', 'resting']);
      expect(result).toBe('esting');
    });

    it('should handle identical strings', () => {
      const result = lengthOfLongestSubstring(['same', 'same']);
      expect(result).toBe('same');
    });
  });

  describe('Multiple String Cases', () => {
    it('should find common substring among multiple strings', () => {
      const result = lengthOfLongestSubstring(['flower', 'flow', 'flight']);
      expect(result).toBe('fl');
    });

    it('should find single character common substring', () => {
      const result = lengthOfLongestSubstring(['dog', 'racecar', 'car']);
      expect(result).toBe('');
    });

    it('should handle strings with no common substring', () => {
      const result = lengthOfLongestSubstring(['abc', 'def', 'ghi']);
      expect(result).toBe('');
    });

    it('should find common substring in unsorted array', () => {
      const result = lengthOfLongestSubstring(['interspecies', 'interstellar', 'interstate']);
      expect(result).toBe('inters');
    });

    it('should handle case with all strings identical', () => {
      const result = lengthOfLongestSubstring(['test', 'test', 'test', 'test']);
      expect(result).toBe('test');
    });
  });

  describe('Complex Cases', () => {
    it('should handle strings of different lengths correctly', () => {
      const result = lengthOfLongestSubstring(['programming', 'program', 'programmer']);
      expect(result).toBe('program');
    });

    it('should find substring at the beginning', () => {
      const result = lengthOfLongestSubstring(['prefix123', 'prefix456', 'prefix789']);
      expect(result).toBe('prefix');
    });

    it('should find substring in the middle', () => {
      const result = lengthOfLongestSubstring(['abcDEFghi', 'xyzDEFuvw', 'mnoDEFpqr']);
      expect(result).toBe('DEF');
    });

    it('should find substring at the end', () => {
      const result = lengthOfLongestSubstring(['123suffix', '456suffix', '789suffix']);
      expect(result).toBe('suffix');
    });

    it('should handle special characters', () => {
      const result = lengthOfLongestSubstring(['hello@world.com', 'test@world.com', 'user@world.com']);
      expect(result).toBe('@world.com');
    });

    it('should handle numbers and letters', () => {
      const result = lengthOfLongestSubstring(['abc123def', 'xyz123uvw', 'mno123pqr']);
      expect(result).toBe('123');
    });
  });

  describe('Performance and Sorting', () => {
    it('should handle array with strings in different order', () => {
      const result1 = lengthOfLongestSubstring(['long string here', 'short', 'medium length']);
      const result2 = lengthOfLongestSubstring(['short', 'medium length', 'long string here']);
      expect(result1).toBe(result2);
    });

    it('should work with large number of strings', () => {
      const strings = new Array(10).fill('commonpart').map((str, i) => `${str}${i}`);
      strings.push('commonpart999'); // Add one more with the common part
      const result = lengthOfLongestSubstring(strings);
      expect(result).toBe('commonpart');
    });
  });

  describe('Unicode and Special Cases', () => {
    it('should handle unicode characters', () => {
      const result = lengthOfLongestSubstring(['café☕morning', 'café☕evening', 'café☕night']);
      expect(result).toBe('café☕');
    });

    it('should handle whitespace', () => {
      const result = lengthOfLongestSubstring(['hello world', 'hello universe', 'hello galaxy']);
      expect(result).toBe('hello ');
    });

    it('should handle single character strings', () => {
      const result = lengthOfLongestSubstring(['a', 'a', 'a']);
      expect(result).toBe('a');
    });

    it('should handle mixed single and multi character strings', () => {
      const result = lengthOfLongestSubstring(['a', 'ab', 'abc']);
      expect(result).toBe('a');
    });
  });
});
