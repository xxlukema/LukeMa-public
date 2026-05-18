

// Abstract class DAO Factory
public abstract class DAOFactory
{
   // List of DAO types supported by the factory
   public static final int MY_SQL = 0;
   public static final int ORACLE = 1;
   public static final int MS_SQLSERVER = 2;

   // There will be a method for each DAO that can be 
   // created. The concrete factories will have to 
   // implement these methods.
   public abstract CustomerDAO getCustomerDAO();
   public abstract AccountDAO getAccountDAO();
   public abstract OrderDAO getOrderDAO();

   public static DAOFactory getDAOFactory(int whichFactory)
   {

      switch (whichFactory)
      {
      case CLOUDSCAPE: 
         return new CloudscapeDAOFactory();
      case ORACLE    : 
         return new OracleDAOFactory();      
      case SYBASE    : 
         return new SybaseDAOFactory();
         ...
      default           : 
         return null;
      }
   }
}

public class CloudscapeDAOFactory extends DAOFactory
{
   public static final String DRIVER = "COM.cloudscape.core.RmiJdbcDriver";
   public static final String DBURL = "jdbc:cloudscape:rmi://localhost:1099/CoreJ2EEDB";

   public CustomerDAO getCustomerDAO()
   {
      return new CloudscapeCustomerDAO();
   }

   public AccountDAO getAccountDAO()
   {
      return new CloudscapeAccountDAO();
   }

   public OrderDAO getOrderDAO()
   {
      return new CloudscapeOrderDAO();
   }
}

public class TestDriver
{
   public static void main(String [] args)
   {
      DAOFactory cloudscapeFactory =  DAOFactory.getDAOFactory(DAOFactory.DAOCLOUDSCAPE);

      CustomerDAO custDAO =cloudscapeFactory.getCustomerDAO();

      int newCustNo = custDAO.insertCustomer(...);
   }

}



