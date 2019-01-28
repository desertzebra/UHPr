package org.khu.uclab.uhp.uhpr.seeder.util;

import java.util.Random;

public class RandomGenerator {
	Random rand = new Random();
	
	public int randInt(int byend) {	// 0 <= x < byend
		return rand.nextInt(byend);
	}
	
	public int randInt(int start, int end) {	// start <= x <= end
		return rand.nextInt(end - start) + start + 1;
	}
	
	public float randFloat(int start, int end) {	// start <= x <= end + 0.xxx
		return rand.nextInt(end - start) + start + 1 + rand.nextFloat();
	}
	
	public int randBool() {	// x = 0 or x = 1
		return rand.nextInt(2);
	}
}
