package com.learn.common.util;


import java.awt.BasicStroke;
import java.awt.Stroke;


public class ChartConstants
{
   public static final int    HALF_CYCLE           = 5;

   public static final Stroke STROKE1              = new BasicStroke(1);

   public static final Stroke STROKE2              = new BasicStroke(2);

   public static final Stroke STROKE4              = new BasicStroke(4);

   public static final int    FrameTopEdge         = 20;

   public static final int    FrameSideEdge        = 2;

   public static final int    FrameHeight          = 836;

   public static final int    FrameWidth           = 1255;

   public static final int    PanelHeight          = 1100;

   public static final int    PanelWidth           = FrameWidth - 2 * FrameSideEdge;

   public static final int    HistoryChartHeight   = 200;

   public static final int    IndicatorChartHeight = 50;

   public static final int    ChartSpacer          = 16;

   public static final int    X0Position           = ChartConstants.FrameSideEdge;
}
