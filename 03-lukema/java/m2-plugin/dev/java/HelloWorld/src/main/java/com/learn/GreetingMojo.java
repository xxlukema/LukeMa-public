package com.learn;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * For parameters:
 *   1. First try to use plugin configuration in the pom.xml for the value.
 *   2. If the parameter is not set in pom.xml plugin configuration, then it will try to use the value of expression.
 *   3. If the expression returns null, then it will try to use the value of default-value (default-value can also use expression)
 *   4. To use pom values for expression, use: expression="${project.name}" or default-value="${project.name}"
 *
 * @goal hi
 * @description Says "Hi" to the user
 */
public class GreetingMojo extends AbstractMojo 
{
   /**
   * The greeting to display.
   * @parameter expression="${name}" default-value="Use -Dname=\"Luke Ma\""
   * @required
   * @readonly
   */
   private String greeting;

   public void execute() throws MojoExecutionException 
   {
      getLog().info("Hello, World. "+greeting);
   }
}

