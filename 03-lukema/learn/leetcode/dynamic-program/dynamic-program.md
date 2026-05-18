# Dynamic Program

[5 hours Dynamic Programming - Learn to Solve Algorithmic Problems & Coding Challenges]<https://www.youtube.com/watch?v=oBt53YbR9Kk>

## Dynamic Programs from Leetcode

For most people, it's easiest to start by coming up with a recursive brute-force solution and then adding memoization to it. 
After that, they then figure out how to convert it into an (often more desired) bottom-up tabulated algorithm.

## Characteristics of Dynamic Programming

### 1. Overlapping Subproblems

### 2. Optimal Substructure Property

## Two Dynamic Programming Methods

### 1. Top-down with Memoization

### 2. Bottom-up with Tabulation

## Alvin's Memorization Recipe

#### 1. Make it work.

    * visualize the problem as a tree
    * implement the tree using recursion
    * test it

#### 2. Make it efficient

    * add a memo object
    * add a base case to return memo value
    * store return value into the memo

## Kind of problems

#### canSum -> Can I do it? yes/no -> Decision Problem

#### howSum -> How will I do it? -> Combinatoric Problem

#### bestSum -> What is the 'best' way to do it? -> Optimization Problem

## Tabulation Recipe

There is no "Make it work and then make it efficient" approach for tabulation.
Everything is done in one step.

    * visualize the problem as a table
    * size the table based on the inputs
    * initialize the table with default values
    * seed the trivial answer into the table
    * iterate through the table
    * fill further positions based on the current position

## Dynamic Programming

    * notice any verlapping subproblems
    * decide what is the trivially smallest input
    * think recursively to use Memorization
    * or think iteratively to use Tabulation
    * draw a strategy first!!!









