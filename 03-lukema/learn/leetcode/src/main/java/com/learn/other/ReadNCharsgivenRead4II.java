package com.learn.other;


/**
 * LC - 157 - Read N Chars Given Read4 II - Call Multiple Times
 * 
 * Hard
 * 
 * Given a file and assume that you can only read the file using a given method read4, implement a method to read n characters.
 * Method read4:
 * The API read4 reads four consecutive characters from file, then writes those characters into the buffer array buf4.
 * The return value is the number of actual characters read.
 * Note that read4() has its own file pointer, much like FILE *fp in C.
 * Definition of read4:
 *     Parameter:  char[] buf4
 *     Returns:    int
 * buf4[] is a destination, not a source. The results from read4 will be copied to buf4[].
 * Below is a high-level example of how read4 works:
 * 
 * File file("abcde"); // File is "abcde", initially file pointer (fp) points to 'a'
 * char[] buf4 = new char[4]; // Create buffer with enough space to store characters
 * read4(buf4); // read4 returns 4. Now buf4 = "abcd", fp points to 'e'
 * read4(buf4); // read4 returns 1. Now buf4 = "e", fp points to end of file
 * read4(buf4); // read4 returns 0. Now buf4 = "", fp points to end of file
 * 
 * Method read:
 * By using the read4 method, implement the method read that reads n characters from file and store it in the buffer array buf. Consider that you cannot manipulate file directly.
 * The return value is the number of actual characters read.
 * Definition of read:
 *     Parameters:	char[] buf, int n
 *     Returns:	int
 * buf[] is a destination, not a source. You will need to write the results to buf[].
 * 
 * Note:
 *     Consider that you cannot manipulate the file directly. The file is only accessible for read4 but not for read.
 *     The read function may be called multiple times.
 *     Please remember to RESET your class variables declared in Solution, as static/class variables are persisted across multiple test cases. Please see here for more details.
 *     You may assume the destination buffer array, buf, is guaranteed to have enough space for storing n characters.
 *     It is guaranteed that in a given test case the same buffer buf is called by read.
 * 
 * Example 1:
 * Input: file = "abc", queries = [1,2,1]
 * Output: [1,2,0]
 * Explanation: The test case represents the following scenario:
 * File file("abc");
 * Solution sol;
 * sol.read(buf, 1); // After calling your read method, buf should contain "a". We read a total of 1 character from the file, so return 1.
 * sol.read(buf, 2); // Now buf should contain "bc". We read a total of 2 characters from the file, so return 2.
 * sol.read(buf, 1); // We have reached the end of file, no more characters can be read. So return 0.
 * Assume buf is allocated and guaranteed to have enough space for storing all characters from the file.
 * 
 * Example 2:
 * Input: file = "abc", queries = [4,1]
 * Output: [3,0]
 * Explanation: The test case represents the following scenario:
 * File file("abc");
 * Solution sol;
 * sol.read(buf, 4); // After calling your read method, buf should contain "abc". We read a total of 3 characters from the file, so return 3.
 * sol.read(buf, 1); // We have reached the end of file, no more characters can be read. So return 0.
 * 
 * Constraints:
 *     1 <= file.length <= 500
 *     file consist of English letters and digits.
 *     1 <= n <= 1000
 */
public class ReadNCharsgivenRead4II {

    public static void main(String[] args) {

        // String file = "abcdABCD1234";
        // int n = 12;

        // ReadNCharsgivenRead4II readNCharsgivenRead4II = new ReadNCharsgivenRead4II();
    }

    /**
     * 
     * Runtime: 1 ms, faster than 51.54% of Java online submissions for Read N Characters Given Read4.
     * Memory Usage: 42.1 MB, less than 49.16% of Java online submissions for Read N Characters Given Read4.
     * 
     * Time: O(n)
     * Space; O(4)
     */
    public int read(char[] buf, int n) {
        int counter = 0;

        if (lastIdx > 0) {
            for (int i = lastIdx; i < received; i++) {
                buf[counter++] = recv4[i];
                if (counter == n) {
                    lastCounter = counter;
                    lastIdx = i;
                    return counter;
                }
            }
        }

        while ((received = read4(recv4)) != 0) {
            for (int i = 0; i < received; i++) {
                buf[counter++] = recv4[i];
                if (counter == n) {
                    lastCounter = counter;
                    lastIdx = i;
                    return counter;
                }
            }
        }

        lastCounter = counter;
        lastIdx = 0;

        return counter;
    }

    int received = 0;
    int lastCounter = 0;
    int lastIdx = 0;
    char[] recv4 = new char[4];

    /**
     * Imaginary Built-In
     */
    public int read4(char[] buf) {
        return 0;
    }
}
