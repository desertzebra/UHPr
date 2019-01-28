package org.khu.uclab.uhp.uhpr.seeder.util;

public class NameGenerator extends RandomGenerator {
	static String[] First_male = {"Albert", "Anderson", "Armand", "Ben", "Brant", "Carl", "Chuck", "Cruz", "Daron",
			"Dave", "Drew", "Eddie", "Evan", "Fahad", "Freeman", "Gary", "Gilbert", "Hank", "Harry", "Ian",
			"Jamey", "Jeff", "Jimmy", "Joseph", "Kenny", "Kris", "Lanny", "Lee", "Leon", "Lucio", "Marco", "Martin",
			"Nick", "Parker", "Paul", "Sammuel", "Tom", "Wajahat", "Wayne", "Wesley", "Young", "Clyde"};
	static String[] First_female = {"Adrian", "Alane", "Alexa", "Amanda", "Ann", "Annie", "Barbara", "Bertha",
			"Beverly", "Brittney", "Camilla", "Carol", "Cassie", "Clara", "Donna", "Emma", "Elizabeth",
			"Gail", "Irene", "Kelly", "Laura", "Lia", "Marica", "Megan", "Nancy", "Joann", "Josephine", "Katie",
			"Kim", "Kristen", "Julia", "Sara", "Rose", "Rose", "Rachel", "Peggy", "Nicole", "Bonnie"};
	static String[] Last = {"Smith", "Johnson", "Williams", "Wilson", "Baker", "Cooper", "Howord", "Jenkins",
			"Satti", "Khan", "Steve", "Olson", "Washington", "Simpson", "Holmes", "Nichols", "Andrews", "Willis",
			"Franklin", "Bishop", "Oliver", "Harvey", "Dean", "Hanson", "Lucas", "Holland", "Alvarado", "Hudson",
			"Potter", "Cannon", "Duran", "Patrick", "Charles", "Kane", "Solomon", "Jackson", "Walker"};
	
	public String getRandomName(int gender) {	// Male: 0, Female: 1
		String newName = new String();
		if(gender == 0) {
			newName = First_male[randInt(First_male.length)] + " " +
					Last[randInt(Last.length)];
		}
		else {
			newName = First_female[randInt(First_female.length)] + " " +
					Last[randInt(Last.length)];
		}
		return newName;
	}
	
	public String getRandomFName(int gender) {
		String fName = new String();
		if(gender == 0) {
			fName = First_male[randInt(First_male.length)];
		}
		else {
			fName = First_female[randInt(First_female.length)];
		}
		return fName;
	}
	
	public String getRandomMName(int gender) {
		int exist = randBool();
		String mName = new String();
		if(exist == 1) {
			if(gender == 0) {
				mName = First_male[randInt(First_male.length)];
			}
			else {
				mName = First_female[randInt(First_female.length)];
			}
		}
		else {
			mName = "";
		}
		return mName;
	}
	
	public String getRandomLName(int gender) {
		return Last[randInt(Last.length)];
	}
}
