package search.utils;

import java.util.ArrayList;
import java.util.List;

import search.utils.Xiangliang.XiangliangBuilder;

public class Kongjian {
	private List<Object> weidus = new ArrayList<>();
	
	Kongjian(){
	}

	public static KongjianBuilder build() {
		return new KongjianBuilder();
	}

	public int getWeiduIndex(Object weidu) {
		return weidus.indexOf(weidu) + 1;
	}

	void addWeidu(Object weidu) {
		weidus.add(weidu);
	}

	public XiangliangBuilder buildXiangliang() {
		return new XiangliangBuilder(this);
	}

	public int getWeiduSize() {
		return weidus.size();
	}
	
	public static class KongjianBuilder {
		private Kongjian kj = new Kongjian();

		public KongjianBuilder addWeidu(Object weidu) {
			kj.addWeidu(weidu);
			return this;
		}

		public Kongjian toKongjian() {
			return kj;
		}
	}

}

