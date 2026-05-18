package com.learn.optional;


import java.util.Optional;


public class OptionalInteger {

    public Integer getEmptyValue() {
        Optional<Integer> opt = Optional.empty();
        return opt.orElse(null);
    }

    public Integer getOfValue() {
        Optional<Integer> opt = Optional.of(1);
        return opt.orElse(null);
    }
}
