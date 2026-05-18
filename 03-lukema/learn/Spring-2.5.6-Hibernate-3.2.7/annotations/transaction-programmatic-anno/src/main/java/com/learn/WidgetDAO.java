package com.learn;


import java.util.*;


public interface WidgetDAO
{
   public List getWidgets();

   public void displayWidgets();

   public Widget getWidgetById(Long id);

   public Widget saveWidget(Widget widget);

   public Widget updateWidget(Widget widget);

   public Widget saveOrUpdateWidget(Widget widget);

   public void deleteWidget(Long id);

   public void deleteWidget(Widget widget);

   public void testHQL();
}



