package com.learn.junit5;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@DisabledOnOs({ OS.WINDOWS, OS.SOLARIS, OS.OTHER })
@EnabledOnJre({ JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11 })
@interface ThisTestWillOnlyRunAtLinuxAndMacWithJava9Or10Or11 {
}
