

How do I create a Date of specific time?
------------------------------------------------------



      Calendar c = Calendar.getInstance();
         
      c.add(Calendar.HOUR, -6);  // This will change the time for "c".

      Date date = c.getTime();   // Date backed 6 hours.
      System.out.println(date.toString());

      System.out.println("hour: "+c.get(Calendar.HOUR));
      c.clear();   // This clears "c"'s timer only.

      // The above change to Calendar "c" does not affact the new
      // Calendar instance:
      System.out.println(Calendar.getInstance().getTime().toString());





