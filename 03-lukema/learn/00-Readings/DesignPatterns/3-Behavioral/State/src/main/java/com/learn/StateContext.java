package com.learn;


public class StateContext
{
   private State acceptedState  = new AcceptedState();

   private State requestedState = new RequestedState();

   private State grantedState   = new GrantedState();

   private State state;

   public void acceptApplication()
   {
      this.state = acceptedState;
   }

   public void requestPermission()
   {
      state.requestPermission(this);
   }

   public void grantPermission()
   {
      state.grantPermission(this);
   }

   public String getStatus()
   {
      return state.getStatus();
   }

   public void setState(State state)
   {
      this.state = state;
   }

   public State getAcceptedState()
   {
      return acceptedState;
   }

   public State getGrantedState()
   {
      return grantedState;
   }

   public State getRequestedState()
   {
      return requestedState;
   }
}
