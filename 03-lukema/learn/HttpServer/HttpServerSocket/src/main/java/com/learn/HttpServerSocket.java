package com.learn;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.learn.io.IOResourceManager;


public class HttpServerSocket
{
   private static final int    PORT         = 8090;

   private static final Logger LOG          = Logger.getLogger(HttpServerSocket.class);

   private static final String RESPONSE     = "<html><head><title>Got it</title></head><body><h1>Got it!</h1><p>It's great.</p></body></html>";

   // private static String response;

   private ServerSocket        serverSocket = null;

   public static void main(String[] args)
   {
      //Dom4jUtils dom4jUtils = new Dom4jUtils("wsdl.xml");

      //  response = dom4jUtils.getXML();

      HttpServerSocket httpServerSocket = new HttpServerSocket();

      while (true)
      {
         httpServerSocket.start();
      }
   }

   public HttpServerSocket()
   {
      try
      {
         serverSocket = new ServerSocket(PORT);
      }
      catch (IOException e)
      {
         LOG.error("Could not listen on port: " + PORT, e);
         IOResourceManager.close(serverSocket, "Exception closing serverSocket.");

         System.exit(-1);
      }
   }

   public void start()
   {
      Socket clientSocket = null;

      LOG.info("HttpServerSocket is ready for requests.");

      try
      {
         clientSocket = serverSocket.accept();
      }
      catch (IOException e)
      {
         LOG.error("Accept failed.", e);
         IOResourceManager.close(serverSocket, "Exception closing serverSocket.");
         IOResourceManager.close(clientSocket, "Exception closing clientSocket.");

         System.exit(-1);
      }

      doRequestResponse(clientSocket);

      IOResourceManager.close(clientSocket, "Exception closing clientSocket.");

      LOG.debug("HttpServerSocket completed with Request/Response.");
   }

   public void doRequestResponse(Socket clientSocket)
   {
      InputStream is = null;
      OutputStream os = null;
      InputStreamReader isr = null;
      BufferedReader br = null;

      LOG.info("HttpServerSocket is ready for requests.");

      try
      {
         SocketAddress sa = clientSocket.getRemoteSocketAddress();

         LOG.info("Found Remote SocketAddress: " + sa);

         is = clientSocket.getInputStream();

         os = clientSocket.getOutputStream();
      }
      catch (IOException e)
      {
         LOG.error("Unable to get InputStream/OutputStream.", e);
         System.exit(-1);
      }

      try
      {
         isr = new InputStreamReader(is);

         br = new BufferedReader(isr);

         System.out.println("The content of the InputStream (buffered):\n");

         for (String line = null;;)
         {
            line = br.readLine();

            /**
              * This will not happen for server socket.
              */
            if (line == null)
            {
               break;
            }

            System.out.println(line);

            /**
              * Stop reading once a blank line is hit. This
              * blank line signals the end of the client HTTP
              * headers.
              */
            if (line.length() == 0)
            {
               LOG.debug("Completed with the Request.");

               /**
                * TODO
                */
               os.write(RESPONSE.getBytes());
               //  os.write(response.getBytes());

               LOG.debug("Completed with the Response.");

               break;
            }
         }
      }
      catch (Throwable t)
      {
         LOG.error("Exception reading request from InputStream or writing response to OutputStream.", t);
      }
      finally
      {
         IOResourceManager.close(is, "Exception closing InputStream.");
         IOResourceManager.close(isr, "Exception closing InputStreamReader.");
         IOResourceManager.close(br, "Exception closing BufferedReader.");

         IOResourceManager.close(os, "Exception closing OutputStream.");
      }
   }

   public void finalize()
   {
      IOResourceManager.close(serverSocket, "Exception closing serverSocket.");
   }
}
