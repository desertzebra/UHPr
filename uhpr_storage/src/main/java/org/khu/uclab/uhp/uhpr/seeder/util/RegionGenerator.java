package org.khu.uclab.uhp.uhpr.seeder.util;

public class RegionGenerator extends StringGenerator {
	String[] country = {"Australia", "Brazil", "Canada", "Egypt" , "France", "Greece", "China", "Hungary", "Iceland", "India", "Italy", "Israel", "Ireland", "Japan", "South Korea", "North Korea", "Mexico", "Malaysia", "Pakistan", "Philippines", "Poland", "Portugal", "Russia", "Sweden", "Taiwan", "Turkey", "Ukraine", "United States", "Uzbekistan", "Vietnam", "Zimbabwe"};
	String[] address = {"satellite town", "kachi abadi", "shakrial"};
	String[] city = {"rawalpindi", "peshawar", "islamabad", "quetta", "seoul", "pershawar", "lahore", "sargodha"};
	String[] state = {"punjab", "kpk", "federal", "sindh", "balochiatan", "kashmir"};
	
	public String getRandomPostalCode() {
		String pc = "";
		for(int i=0; i<5; i++) {
			pc += getRandomNumber();
		}
		return pc;
	}
	
	public String getRandomCountry() {
		return country[randInt(country.length)];
	}
	
	public String getRandomAddress() {
		return address[randInt(address.length)];
	}
	
	public String getRandomCity() {
		return city[randInt(city.length)];
	}
	
	public String getRandomState() {
		return state[randInt(state.length)];
	}
	
}
