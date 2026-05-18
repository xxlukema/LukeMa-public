package com.learn.java15;


public sealed class SealedClass permits Circle, Rectangle, Square {

}


final class Circle
    extends SealedClass {

}


non-sealed class Rectangle
    extends SealedClass {

}


non-sealed class Square
    extends SealedClass {

}
