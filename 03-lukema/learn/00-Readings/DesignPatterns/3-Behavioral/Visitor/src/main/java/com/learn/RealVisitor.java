package com.learn;


import org.apache.log4j.Logger;


public class RealVisitor
   implements Visitor
{
   private static final Logger LOG = Logger.getLogger(RealVisitor.class);

   public void visit(ToBeVisited toBeVisited)
   {
      Place yourplace = toBeVisited.accept(this);

      LOG.info("Your visitable data is: " + yourplace.getVisitable());
   }
}
