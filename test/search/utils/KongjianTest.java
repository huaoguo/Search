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
		double cosine = Xiangliang.Cosine(xl,xl2);
		assertEquals(1d, cosine);
		Xiangliang xl3 = kj.buildXiangliang()
				.setValue(1982, 98.576d)
				.setValue(389, 20.74d)
				.toXiangliang();
		double cosine2 = Xiangliang.Cosine(xl,xl3);
		assertNotNull(cosine2);
	}
	
}
