package search.utils;

import java.util.ArrayList;
import java.util.List;

import search.utils.Xiangliang.XiangliangBuilder;

public class Kongjian {
	private List<Integer> weidus = new ArrayList<>();
	
	Kongjian(){
	}

	public static KongjianBuilder build() {
		return new KongjianBuilder();
	}

	public int getWeiduIndex(int dictId) {
		return weidus.indexOf(dictId) + 1;
	}

	void addWeidu(int dictId) {
		weidus.add(dictId);
	}

	public XiangliangBuilder buildXiangliang() {
		return new XiangliangBuilder(this);
	}

	public int getWeiduSize() {
		return weidus.size();
	}
	
	public static class KongjianBuilder {
		private Kongjian kj = new Kongjian();

		public KongjianBuilder addWeidu(int dictId) {
			kj.addWeidu(dictId);
			return this;
		}

		public Kongjian toKongjian() {
			return kj;
		}
	}

}

