package org.khu.uclab.uhp.uhpr.seeder.util;

public class MedicalProblemGenerator extends RandomGenerator{
	//String[] problem = {"asthma", "BCC", "Dermatochalasis", "diabetes", "Dry Eye", "HTN", "hyperlipidemia", "IDDM w/ BDR", "Keratoconus", "NIDDM w/ BDR", "NS Cataract", "POAG", "SCC", "stye"};
	String[] problem = {"HTN", "diabetic foot", "diarrhea", "breathing difficulty", "burning micturition", "pain joints", "neonatal jaundice", "fits and fever", "fever wutg breathing difficulty", "chest pain", "p.v bleeding", "joint pain"};
	//String[] occurrence = {"Unknown or N/A","First", "Early Recurrence (<2 Mo)", "Late Recurrence (2-12 Mo)", "Delayed Recurrence (> 12 Mo)", "Chronic/Recurrent", "Acute on Chronic"};
	String[] occurrence = {"since birth", "years", "days", "weeks", "hours", "months"};
	String[] outcome = {"Unassigned", "Resolved", "Improved", "Status quo", "Worse", "Pending followup"};
	
	public String getRandomProblem() {
		return problem[randInt(problem.length)];
	}
	
	public String getRandomOccurrence() {
		int i = randInt(occurrence.length);
		if(i == 0)
			return occurrence[i];
		return randInt(1, 12) + occurrence[i];
	}
	
	public String getRandomOutcome() {
		return outcome[randInt(outcome.length)];
	}
}
