package com.learn.jsf.controller.push;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class OilWatcher extends BaseQuoteWatcher {
	private static final long serialVersionUID = 1L;

	private String base = "CL";
	private String suffix = ".NYM";

	public String getSymbol() {
		// clf10.nym, CLG10.NYM

		/**
		 * LTD: Last Trading Day LTD Dec 19 LTD Jan 20 LTD Feb 20 LTD Mar 22 LTD
		 * Apr 20 LTD May 20 LTD Jun 22 LTD Jul 20 LTD Aug 20 LTD Sep 21 LTD Oct
		 * 20 LTD Nov 19
		 */

		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);

		/**
		 * int day = calendar.get(Calendar.DAY_OF_MONTH); if (day > 19) {
		 * calendar.add(Calendar.MONTH, 1); }
		 */

		return base + getMonth(calendar) + getYear(calendar) + suffix;
	}

	public static void main(String[] args) {
		String symbol = new OilWatcher().getSymbol();

		System.out.println(symbol);
	}

	public static int getYear(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);

		return year % 100;
	}

	public static char getMonth(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH);

		switch (month) {
		case Calendar.JANUARY:
			return 'F';
		case Calendar.FEBRUARY:
			return 'G';
		case Calendar.MARCH:
			return 'H';
		case Calendar.APRIL:
			return 'J';
		case Calendar.MAY:
			return 'K';
		case Calendar.JUNE:
			return 'M';
		case Calendar.JULY:
			return 'N';
		case Calendar.AUGUST:
			return 'Q';
		case Calendar.SEPTEMBER:
			return 'U';
		case Calendar.OCTOBER:
			return 'V';
		case Calendar.NOVEMBER:
			return 'X';
		case Calendar.DECEMBER:
			return 'Z';
		default:
			return 'o';
		}
	}
}
