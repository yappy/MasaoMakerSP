package yappy.util;
import java.text.*;

/**
 * 整数や実数を指定されたケタまでに切り捨てるユーティリティクラスです。
 * @since 1.1
 */
public final class FormatUtil{
	private FormatUtil(){}
	/**
	 * double値を下 keta ケタまでに切り捨てます。
	 * たとえば、小数点以下3ケタまでの数にしたいのなら keta に 1000 を渡します。<br>
	 * 例:<br>
	 * FormatUtil.format( 1.23456 , 3 ) → 1.234<br>
	 * FormatUtil.format( -9.87654 , 2 ) → -9.87<br>
	 * FormatUtil.format( 5.99 , 0 ) → 5<br>
	 * @since 1.1
	 * @param val フォーマット対象実数
	 * @param keta 小数点以下何ケタまでにするか
	 * @return 切り捨てられた文字列
	 */
	public static final String format(double val,int keta){
		double d=(double)((int)(val*Math.pow(10,keta)));
		double dd=d/(double)Math.pow(10,keta);
		DecimalFormat df=new DecimalFormat();
		df.setMinimumFractionDigits(keta);
		return df.format(dd);
	}
	/**
	 * int値を keta の位までに切り捨てます。
	 * たとえば、千の位までの数にしたいのなら keta に 1000 を渡します。<br>
	 * 警告: keta に 10^n(nは任意の数) 以外の数値を渡すとおかしくなります。<br>
	 * 例:<br>
	 * FormatUtil.format( 123456 , 1000 ) → 123000<br>
	 * FormatUtil.format( -987654 ,100  ) → -987600<br>
	 * FormatUtil.format( 123456 , 1 ) → 123456<br>
	 * @since 1.1
	 * @param val フォーマット対象整数
	 * @param keta 何の位までにするか,10の累乗
	 * @return 切り捨てられた文字列
	 */
	public static final String format(int val,int keta){
		int i=(int)(val/keta)*keta;
		return Integer.toString(i);
	}
}