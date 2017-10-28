package testing;

public class NumberCasting {

	public static void main(String[] args) {
		//Java hierarchy is as follows: Integer, Long, Float, Double
		Long d = 5L;
		Long e = 25L;
		Long a=d*e;
		Number c = 0.25D;
		Number q = (Double) c;
		Double f = (double) d;
		System.out.println(a);
		Float ff = 1.0F;
		Double dd = f*1.0D;
		System.out.println(dd);
		System.out.println(dd.getClass());
	}

}
