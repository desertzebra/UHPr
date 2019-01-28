package org.khu.uclab.uhp.uhpr.seeder.util;

public class GenderGenerator extends RandomGenerator {
	public int getRandomGender() {	// generate random gender and return 0 or 1 (0: male, 1: female)
		return randBool();
	}
	
	public String getGenderString(int gender, String male, String female) {	// convert 0 -> male, 1 -> female
		String gender_s = new String();
		if (gender == 0) {
			gender_s = male;
		}
		else {
			gender_s = female;
		}
		return gender_s;
	}
	
	public String getRandomGender(String male, String female) {	// generate random gender and return specific String
		return this.getGenderString(this.getRandomGender(), male, female);
	}
	
	public int getRandomMarried() {
		return randBool();
	}
	
	public String getMarriedString(int status, String married, String unmarried) {
		String status_s = new String();
		if (status == 0) {
			status_s = unmarried;
		}
		else {
			status_s = married;
		}
		return status_s;
	}
	
	public String getTitle(int gender, int married) {	// generate random title for each gender
		String title = new String();
		if(gender == 0)
			title = "Mr.";
		else
			if(married == 0)
				title = "Ms.";
			else
				title = "Mrs.";
		return title;
	}
}
