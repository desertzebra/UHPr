package org.khu.uclab.uhp.uhpr.seeder.util;

public class PrescriptionGenerator extends RandomGenerator {
	String[] drug = {"pain killer", "investigations", "foot care", "antihypertensives", "I.V fluids", "I.V antibiotic", "oxygen", "nebulization", "antibiotic", "anti-inflammatory", "anticovulsant", "ECG", "CXR", "blood transfusion"};
	String[] unit = {"mg", "mg/1cc", "mg/2cc", "mg/3cc", "mg/4cc", "mg/5cc", "mcg", "grams", "mL"};
	
	public String getRandomDrug() {
		return drug[randInt(drug.length)];
	}
	
	public String getRandomUnit() {
		return String.valueOf(randInt(1, 100)) + unit[randInt(unit.length)];
	}
}
