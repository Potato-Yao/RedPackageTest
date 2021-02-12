package Tools;

import java.util.Random;

public class RedPackage
{
	public static double getRandomMoney(LeftMoneyPackage leftMoneyPackage)
	{
//		int remainSize  = 10; // remainSize 剩余的红包数量
//		int remainMoney = 100;// remainMoney 剩余的钱
		if (leftMoneyPackage.remainSize == 1)
		{
			leftMoneyPackage.remainSize--;
			return (double) Math.round(leftMoneyPackage.remainMoney * 100) / 100;
		}
		Random r = new Random();
		double min = 0.01; //
		double max = leftMoneyPackage.remainMoney / leftMoneyPackage.remainSize * 2;
		double money = r.nextDouble() * max;
		money = money <= min ? 0.01 : money;
		money = Math.floor(money * 100) / 100;
		leftMoneyPackage.remainSize--;
		leftMoneyPackage.remainMoney -= money;
		return money;
	}
}

class LeftMoneyPackage
{
    int remainSize = 10;
    int remainMoney = 100;
}

