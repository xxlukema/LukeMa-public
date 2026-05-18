# TODO

## Revisit CombSum

## Revisit SudokuSolver

## Undertsand dp

## Understand backtracking

## Google Interview Coverage

    Sliding Window, Graphs, Trie, Permutations/Combinations, DP

## REPTCO

1. Repeat: make sure you do understand the problem.
2. Example: get insights by doing examples
3. Approach: come up with your approach(es) to the problem (brute force first)
4. Code: write the code for your chosen approach
5. Testing: pass the testcases
6. Optimize: optimize the complexities (time and space) of your algorithm

## Top 7 Amazon Interview Questions + Explanations (with Ex- Amazon + Google + Facebook + Microsoft)

[Top 7 Amzn Questions]<https://www.youtube.com/watch?v=HvMc-ECHTWk>

1:00 - #1 How to answer WHY AMAZON vs. what to avoid!
2:30 - #2 The most innovative thing you have done?
4:22 - #3 The most complex problem you have solved?
5:50 - #4 What is your WEAKNESS? (Think of a STRENGTH!)
7:34 - #5 Career Failure (Your Career Mistake)
8:45 - #6 Building a product or program from beginning to end?
11:27 - #7 Disagreement with your manager (Oh, Yes!)

## Amazon Leadership Principles

[Amazon Leadership Principles]<https://www.youtube.com/watch?v=EVbjvA7VJEY>

## Amazon 14 Principles

[Amazon 14 Principles]<https://www.youtube.com/watch?v=z24omk_d-t8>

### RE-DO List

1. LC - 90 - Redo all algos.
2. LC - 6 - Zig Zag
3. LC - 96 - redo
4. LC - 95 - redo
5. LC - 119 - Triangle - Why one of the tow memos has error?
6. LC - 123 - Best Time To Buy and Sell Stocks - fix failed "maxProfitLukeOneWay()"
7. LC - 155 - Min Stack --- Two tricks: (1) Use ValueMin record, (2) Use two stacks and make the minStack's elements as List to handle duplicaed mins.
8. LC - 126 - Word Ladder II --- Re-work. ------- timeout
9. LC - 127 - Word Ladder --- Skipped. ------- timeout
10. LC - 130 - Re-work
11. LC - 136 - Single Number --- Trick of XOR
12. LC - 140 - If have time, revisit "Recursive Encoding Approach"
13. LC - 143 - fast/slow pointers
14. LC - 144 - Morris Tree Preorder Traversal
15. LC - 145 - Morris Tree Postorder Traversal - Morris has error
16. LC - 156 - Binary Tree Upside Down --- Redo
17. LC - 162 - Max Gap: Radix Sort (Positive numbers)
18. LC - 167 - Two Sum II - Input Array Is Sorted --- Smart idea (Two Pointers) !!!
19. LC - 168 - Excel Sheet Column Title --- Looks easy, but takes hours to make it work correctly.
20. LC - 169 - Boyer-Moore Voting Algorithm
21. LC - 174 - Dungeon Game --- Not completed. Continue with the work
22. LC - 175 - Combine Two Tables ---
23. CL - 211 - Word Search With '.' - Why my implementation Timed Out?
24. LC - 212 - Word Search II --- Why both my implementations timeout?
25. LC - 216 - Combination Sum III --- Remember the **KEY TRICK** to avoid repeating and duplications.
26. LC - 227 - Basic Calculator II --- Not completed
27. LC - 228 - Summary Range --- Not completed
28. LC - 229 - Majority Element II --- Not completed
29. LC - 87 - Scramble String --- Not completed. No official solution.
30. LC - 254 - Factor Combinations --- Worth re-do --- Common for all number permutations
31. LC - 255 Verify Preorder Sequence In Binary Search Tree --- Worth review to better understand pre-order traversal
32. LC - 256 Paint House --- Tricky to get the recursion formular
33. LC - 257 Binary Tree Path --- Convert recursion to iteration
34. LC - 260 Single Number III --- Bit Ops
35. LC - 264 Paint House II --- Worth redo
36. LC - 269 Alien Dictionary --- Redo (Research BFS for redo) --- Three Tricks:

    - Trick 1: white/grey/black coloring cyclic detection.
    - Trick 2: Post Order DFS in combination with White/Grey/Black cyclic graph detection
    - Trick 3: Put all chars into adjMap

37. LC - 301 Remove Invalid Parentheses --- Trick 1: One way. Trick 2: `countLeft`, `countRight`, with `countRight > countLeft` as invalid expression.
38. LC - 303 Range Sum Query - Immutable --- Trick: cache sum[]
39. LC - 304 Range Sum Query 2D - Immutable --- trick cache sum[][]
40. LC - 305 Number of Islands II --- Redo LC `UnionFind` class

    - Trick 1: Use DisjointSet Uion Find.
    - Trick 2: Use Re-use the counter can save the steps of whole path update.
    - Trick 3: Convert 2D array to 1D array.
    - Trick 4: Understand LC `UnionFind`. Rewrite `UnionFind` class for int[].
    - Trick 5: For Luke's version of `DisjointSetUnionIntArr`, **Compress** inside union makes runtime `20x` faster than **without compress**

41. LC - 356 Additive Number --- No official solution.
42. LC - 306 Range Sum Query - Mutable --- Time Limit Exceeded
43. LC - 309 Best Time to Buy and Sell Stock with Cooldown --- Worth re-do. (1) TopDown vs ButtomUp. (2) The ways of thinking.

    - Trick 1: array is much faster than record/Map (25 times faster)
    - Trick 2: Math.max() is much faster than Collections.max(List.of(...))

44. LC - 310 Minimum Height Trees --- backtrack with memo (TopDown) will timeout

    - Trick 0: BFS
    - Trick 2: For the tree-alike graph, the number of centroids is no more than 2.
    - Trick 3: Leaf's parent is potentially new leaf for next round. Therefor, do not search for leaves from all vertices.

45. LC - 311 Sparse Matrix Multiplication --- How to effectively use space for sparse array?

    - Trick 1: How to effectively use space for sparse array?
    - Trick 2: Use Map<Cell, Integer> to store non-zero values. This saves space of array.
    - Trick 3: Map/Cell operation is minimum 10x slower than array operations.

46. LC - 312 Burst Ballons --- Redo: Do not understand Bottom Up

    - Trick 1: prepend and suspend nums with `1`s to save boundary checks
    - Trick 2: The last ballon to burst is `idx`
    - Trick 3: `[left, i], [i], [i, right]` inclusive
    - Trick 4: **!Important**: Not `+ nums[i - 1] * nums[i] * nums[i + 1]`, because i is the last to burst.

47. LC - 314 Binary Tree Vertical Traversal

    - Trick 1: Use colMap
    - Trick 2: BFS

48. LC - 2300. Successful Pairs of Spells and Potions

    Not completed

49. `BFS` for Shortest Path - Sample: `src\main\java\com\learn\lc75\NearestExitFromEntrance.java` LC-1926

    - Trick: Mark the Node/Cell `Visited` immediately after enque the Node/Cell.

50. LC - 875. Koko Eating Bananas

    - Trick 1: Use binary Search
    - Trick 2: Use `left = mid + 1`
    - Trick 3: Use `if (hours <= h) {` to enable further seek for min solution.
    - Trick 4: Never use `while (left <= right) {`. Always use `while (left < right) {`
    - Trick 5: Use `return right;` to converge.

<pre>
Runtime: 406 ms, faster than 67.40% of MySQL online submissions for Combine Two Tables.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Combine Two Tables.

# Write your MySQL query statement below

select p.firstName, p.lastName, a.city, a.state
from person as p
left outer join address as a
on p.personId = a.personId;
</pre>

## `radis` vs `hazelcast`

`spring-boot-cache-redis-hazelcast`

## `eureka`

`spring-boot-eureka-school`

## `CDN` Caching

`pull`

`push`

#####################################
######## Interview Questions ########
#####################################

## Behavior Interview Questions

1. Video ON
2. Patience
3. Leading conversation
4. Under pressure (prioritize)
5. Pursade a lead

## NG Interview Questions

1. Form validations
2. Inter-component communication - cross components/pages
3. NG Testing (Selenium, Protractor, Cypress, Jasmine/karma, test reports)
4. Lint: const, let, var, '', ""
5. ngClass vs class
6. css vs scss
7. MVVM

## Java Interview Questions

1. Design Patterns
2. Algorithms
3. REST Exception Handling
4. Mockito
5. Gradle vs Maven
6. OOD
7. SOA
8. Design a large system
9. Design a parking lot

## Database

1. Third largest salary in department
2. Nomalization and denormalization
3. DB tuning
4. Partition: Index, Table
