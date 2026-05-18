package com.learn;


import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;


/**
 * For parameters: 
 *   1. First, it tries to use plugin configuration in the pom.xml for the value. 
 *   2. If the parameter is not set in pom.xml plugin configuration, it will try to use the value of expression. 
 *   3. If the expression returns null, then it will try to use the value of default-value 
 *      (default-value can also use expression) 
 *   4. To use pom values for expression, use like this:
 *      expression="${project.name}" or default-value="${project.name}"
 * 
 * @goal list
 * @description Echo parameter values
 */
public class ParmsMojo
   extends AbstractMojo
{
   /**
    * The greeting to display.
    * 
    * @parameter expression="${project.name}"
    * @required
    */
   private String             greeting;

   /**
    * POM
    * 
    * @parameter expression="${project}"
    * @required
    * @readonly
    */
   protected MavenProject     project;

   /**
    * @component role="org.apache.maven.artifact.factory.ArtifactFactory"
    */
   protected ArtifactFactory  factory;

   /**
    * @component role="com.learn.HelloWorld"
    */
   protected HelloWorld       hello;

   /**
    * @parameter expression="${project.artifactId}"
    * @required
    */
   protected String           artifactId;

   /**
    * @parameter expression="${project.dependencies}"
    * @required
    * @return
    */
   protected List<Dependency> dependencyArtifacts;

   public void execute()
      throws MojoExecutionException
   {
      getLog().info("Hello, World. " + greeting);

      getLog().info("artifactId: " + artifactId);

      for (Dependency d : dependencyArtifacts)
      {
         getLog().info(d.toString());
      }

      getLog().info(project.toString());

      getLog().info(factory.ROLE);

      // http://techkriti.wordpress.com/2007/06/28/maven2-hibernate-plugin-and-spring/

      getLog().info(hello.getMyString());
   }
}
