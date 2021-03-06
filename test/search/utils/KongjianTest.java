package search.utils;

import junit.framework.TestCase;

public class KongjianTest extends TestCase {
	private Kongjian kj;
	
	public void setUp(){
		kj = Kongjian.build().addWeidu(188).addWeidu(389).addWeidu(1982).toKongjian();
	}

	public void testBuild(){
		assertNotNull(kj);
		assertEquals(1, kj.getWeiduIndex(188));
		assertEquals(2, kj.getWeiduIndex(389));
		assertEquals(3, kj.getWeiduIndex(1982));
	}
	
	public void testBuildXiangliang(){
		Xiangliang xl = kj.buildXiangliang()
				.setValue(1982, 35.372d)
				.setValue(389, 87.43921d)
				.toXiangliang();
		assertNotNull(xl);
		assertEquals(0d, xl.getWeight(1));
		assertEquals(87.43921d, xl.getWeight(2));
		assertEquals(35.372d, xl.getWeight(3));
		assertEquals(3, kj.getWeiduSize());
	}
	
	public void testCosine(){
		Xiangliang xl = kj.buildXiangliang()
				.setValue(1982, 35.372d)
				.setValue(389, 87.43921d)
				.toXiangliang();
		Xiangliang xl2 = kj.buildXiangliang()
				.setValue(1982, 35.372d)
				.setValue(389, 87.43921d)
				.toXiangliang();
		double cosine = Xiangliang.cosine(xl,xl2);
		assertEquals(1d, cosine);
		Xiangliang xl3 = kj.buildXiangliang()
				.setValue(1982, 98.576d)
				.setValue(389, 20.74d)
				.toXiangliang();
		double cosine2 = Xiangliang.cosine(xl,xl3);
		assertNotNull(cosine2);
	}
	
	public void testCosine2(){
		Kongjian kj = Kongjian.build().addWeidu(1690).addWeidu(1847).addWeidu(22283).toKongjian();
		Xiangliang xl = kj.buildXiangliang().setValue(1690, 4.54696154482131)
				.setValue(1847, 4.6920118776246)
				.setValue(22283, 9.30498875451535).toXiangliang();
		Xiangliang xl1 = kj.buildXiangliang().setValue(1690, 4.54696154482131)
				.setValue(1847, 4.6920118776246).toXiangliang();
		Xiangliang xl2 = kj.buildXiangliang().setValue(1690, 4.54696154482131)
				.setValue(1847, 4.54696154482131)
				.setValue(22283, 9.30498875451535).toXiangliang();
		double c1 = Xiangliang.cosine(xl, xl1);
		double c2 = Xiangliang.cosine(xl, xl2);
		assertTrue(c1 < c2);
	}
	
	public void testCosine3(){
		Kongjian kj = Kongjian.build().addWeidu("幽鬼").addWeidu("出").addWeidu("装").toKongjian();
		Xiangliang xl = kj.buildXiangliang().setValue("幽鬼", 9.12379809125185)
				.setValue("出", 3.2580648204001)
				.setValue("装", 4.38233110485071).toXiangliang();
		Xiangliang xl1 = kj.buildXiangliang().setValue("幽鬼", 3.71468922286683)
				.setValue("出", 1.32649781973432).setValue("装", 1.81553717200958).toXiangliang();
		Xiangliang xl2 = kj.buildXiangliang().setValue("幽鬼", 4.92033397063939)
				.setValue("出", 1.33813376552147).setValue("装", 1.79988598949226).toXiangliang();
		double c1 = Xiangliang.cosine(xl, xl1);
		double c2 = Xiangliang.cosine(xl, xl2);
		assertTrue(c1 > c2);
	}
	
	public void testCosine4(){
		Kongjian kj = Kongjian.build().addWeidu(1).toKongjian();
		Xiangliang xl = kj.buildXiangliang().setValue(1, 7.03633525000152d).toXiangliang();
		Xiangliang xl2 = kj.buildXiangliang().setValue(1, 2.85256834459521d).toXiangliang();
		System.out.println(Xiangliang.cosine(xl, xl2));
	}
	
}
