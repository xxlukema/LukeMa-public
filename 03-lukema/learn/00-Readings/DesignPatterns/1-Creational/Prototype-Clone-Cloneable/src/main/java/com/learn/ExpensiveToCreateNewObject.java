package com.learn;


import org.apache.log4j.Logger;


public class ExpensiveToCreateNewObject
   implements Cloneable
{
   private static final Logger                     LOG                          = Logger.getLogger(ExpensiveToCreateNewObject.class);

   /**
    * A prototype instance to store the expensive resource.
    */
   private static final ExpensiveToCreateNewObject PROTOTYPE                    = new ExpensiveToCreateNewObject();

   private String                                  expensiveResourceShallowCopy = null;

   private String                                  cheapResourceDeepCopy        = null;
                                                                                                                                                    
   /**
    * Private constructor
    */
   private ExpensiveToCreateNewObject()
   {
      LOG.info("***************** Construtor called. This should be called only once. *****************");
      
      doExpensiveSetup();
      
      setCheapResourceDeepCopy("Cheap resource deep copy 111111111.");
   }

   /**
    * Private setup/init for expensive resource.
    */
   @PostConstruct
   private void doExpensiveSetup()
   {
      LOG.info("***************** doExpensiveSetup called. This should be called only once. *****************");

      setExpensiveResourceShallowCopy("Expensive resource shallow copy 111111111.");
   }

   public static ExpensiveToCreateNewObject getPROTOTYPE()
   {
      return PROTOTYPE;
   }

   public ExpensiveToCreateNewObject clone()
      throws CloneNotSupportedException
   {
      ExpensiveToCreateNewObject instance = (ExpensiveToCreateNewObject) super.clone();

      /**
       * By default, it does a shallow copy. Therefore, shallow no need to call setter/getter.
       */
      // instance.setExpensiveResourceShallowCopy(PROTOTYPE.getExpensiveResourceShallowCopy());
      instance.setCheapResourceDeepCopy("Cheap resource deep copy 222222222.");

      return instance;
   }

   public void setExpensiveResourceShallowCopy(String expensiveResourceShallowCopy)
   {
      this.expensiveResourceShallowCopy = expensiveResourceShallowCopy;
   }

   public String getExpensiveResourceShallowCopy()
   {
      return expensiveResourceShallowCopy;
   }

   public void setCheapResourceDeepCopy(String cheapResourceDeepCopy)
   {
      this.cheapResourceDeepCopy = cheapResourceDeepCopy;
   }

   public String getCheapResourceDeepCopy()
   {
      return cheapResourceDeepCopy;
   }
}
