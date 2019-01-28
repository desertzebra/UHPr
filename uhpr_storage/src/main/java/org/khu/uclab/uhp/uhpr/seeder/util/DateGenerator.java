package org.khu.uclab.uhp.uhpr.seeder.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class DateGenerator extends RandomGenerator {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Date date = new Date();
	
	public String getCurYear() {
		int curYear = 2018;
		return String.format("%02d", curYear);
	}
	
	public String getCurMonth() {
		int curMonth = Calendar.MONTH;
		return String.format("%02d", curMonth);
	}
	
	public String getCurDay() {
		int curDay = Calendar.DAY_OF_MONTH;
		return String.format("%02d", curDay);
	}
	
	public String getCurDate() {
		return getCurYear() + getCurMonth() + getCurDay();
	}
	
	public String getRandomBirthDateByAge(int age) {
		int dob_y = 2018 - age;
		int dob_m = randInt(1, 12);
		int dob_d = randInt(1, 28);
		return String.valueOf(dob_y) + String.format("%02d", dob_m) + String.format("%02d", dob_d);
	}
	
	public int getAgeFromDOB(String DOB) {
		return Integer.valueOf(this.getCurYear()) - Integer.valueOf(DOB.substring(0, 4));
	}
		
	public ArrayList<String> getRandomPeriodicDateInYear(int year) {
		// begin date
		int mon1 = randInt(1, 11);
		int day1 = randInt(1, 27);
		// end date
		int mon2 = randInt(mon1, 12);
		int day2 = randInt(day1, 28);
		// still active? 1: true, others: false => activate end date or not
		int Still = randInt(1, 4);
		
		ArrayList<String> dates = new ArrayList<String>();
		
		dates.add(String.valueOf(year) + String.format("%02d", mon1) + String.format("%02d", day1));
		// if still active, no end date
		if (Still == 1)
			dates.add("");
		else
			dates.add(String.valueOf(year) + String.format("%02d", mon2) + String.format("%02d", day2));
		
		return dates;
	}
	
	public String getRandomDateInYear(int year) {
		int mon = randInt(1, 12);
		int day = randInt(1, 28);
		
		return String.valueOf(year) + String.format("%02d", mon) + String.format("%02d", day);
	}
}
