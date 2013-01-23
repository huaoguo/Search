package search.utils;

import java.util.HashMap;
import java.util.Map;

public class Xiangliang {
	private Kongjian kj;
	private Map<Integer, Double> weights = new HashMap<Integer, Double>();

	public Xiangliang(Kongjian kj) {
		this.kj = kj;
	}

	void setValue(int index, double weight) {
		weights.put(index, weight);
	}

	public double getWeight(int index) {
		Double value = weights.get(index);
		if (value == null) {
			return 0d;
		}
		return value;
	}

	public static double cosine(Xiangliang xl, Xiangliang xl2) {
		if (!xl.kj.equals(xl2.kj)) {
			throw new RuntimeException("can not be compared!");
		}
		double fenzi = 0d;
		double fenmu1 = 0d;
		double fenmu2 = 0d;
		for (int i = 0; i < xl.kj.getWeiduSize(); i++) {
			fenzi += xl.getWeight(i + 1) * xl2.getWeight(i + 1);
			fenmu1 += Math.pow(xl.getWeight(i + 1), 2);
			fenmu2 += Math.pow(xl2.getWeight(i + 1), 2);
		}
		double fenmu = Math.sqrt(fenmu1 * fenmu2);
		return fenzi / fenmu;
	}
	
	public static class XiangliangBuilder {
		private Kongjian kj;
		private Xiangliang xl;

		public XiangliangBuilder(Kongjian kj) {
			this.kj = kj;
			xl = new Xiangliang(kj);
		}

		public XiangliangBuilder setValue(Object weidu, double weight) {
			int index = kj.getWeiduIndex(weidu);
			xl.setValue(index, weight);
			return this;
		}

		public Xiangliang toXiangliang() {
			return xl;
		}

	}

}