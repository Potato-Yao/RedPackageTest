package Try;

import java.util.Random;

/**
 * @author Potato Yao
 */

public class RedPackageTest
{
	/**
	 * 最简单的分配方法，单位全部统一为 分
	 * @param number 红包总数
	 * @param money  金额总数
	 */
	public void redPacket1(int number, int money)
	{
		int minCount = 1;  //最低金额
		int moneyCount = money * 100 - minCount * number;  //分配给各个红包 0.01后可分配金额
		Random random = new Random();
		float[] array = new float[number];  //储存红包金额的数组
		StringBuffer arrayStr = new StringBuffer("");  //为了方便显示
		//为前 number-1 个红包分配金额
		for (int i = 0; i < number - 1; i++)
		{
			try
			{
				array[i] = (minCount + random.nextInt(moneyCount));
			} catch (IllegalArgumentException e)
			{  //防止 moneyCount 为0
				array[i] = minCount;
			}
			if (moneyCount != 0)  //可分配金额可以继续分配
			{
				moneyCount -= array[i];
			}
			arrayStr.append(String.valueOf((double) array[i] / 100) + " ");
		}
		array[number - 1] = moneyCount + minCount;
		arrayStr.append(String.valueOf((double) array[number - 1] / 100) + " ");
		System.out.println(arrayStr);  //将各个红包金额打印出来
	}
}
