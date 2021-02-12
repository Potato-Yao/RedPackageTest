看了 @心有灵犀 的代码，他的代码如下：

```java
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
```



![](F:\untitled\image\大佬的实现.png)

下面是我根据知乎上疑似微信红包实现进行可运行性补充后的代码：

```java
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
```

以及`Test.java`文件：

```java
package Tools;

/**
 * @author Potato Yao
 */

public class Test
{
	public static void main(String[] args)
	{
		LeftMoneyPackage l = new LeftMoneyPackage();

		System.out.println(RedPackage.getRandomMoney(l));
	}
}
```



![F:\untitled\image\carbon (1)](F:\untitled\image\carbon (1).png)

代码均上传至GitHub，网址

[GitHub地址]: https://github.com/Potato-Yao/RedPackageTest	"GitHub地址"

可见，我们的实现方法并不相同，且 @心有灵犀 不在家，无法展示他的测试文件。介于这两点，我认为继续执自己的代码争论就成了一种无意义且幼稚的行为，因此作为证据，我截取了一段`毕导THU`的视频，原视频地址

[毕导THU的原视频]: https://www.bilibili.com/video/av84581638	"毕导的视频"

我截取的视频在附的文件中

由这个视频可见，***微信红包每个人能抢到的金额服从0.01到2倍剩余均值之间的均匀分布***，因此，当样本数量*大于8*时，后抢有绝对的优势

---

出于时间原因，我没有完成我的代码的测试类编写。假设毕导THU视频中的原理和结果是正确的，那么根据我的见解 @心有灵犀 的代码错误如下：

这里是 @心有灵犀 附的他的代码的测试结果：

![](F:\untitled\image\psc.jpg)

在他的结果中，第一个人抢到的红包远高于后抢的人，但这与事实不符：

![](F:\untitled\image\Screenshot_20210212_121945.jpg)

![](F:\untitled\image\Screenshot_20210212_122025.jpg)

![](F:\untitled\image\Screenshot_20210212_122114.jpg)

图源为我家家庭群

那为什么会出这么大的错误呢？

因为这两段代码：

```java
try
{
	array[i] = (minCount + random.nextInt(moneyCount));
} catch (IllegalArgumentException e)
{  //防止 moneyCount 为0
	array[i] = minCount;
}
```

和

```java
if (moneyCount != 0)  //可分配金额可以继续分配
{
	moneyCount -= array[i];
}
```

错误很明显，第一个的红包取决于一个随机数，即对象`random`，`random`并没有对第一个人的限制，这就导致第一个人直接获得一半的钱的概率很大（可通过正态分布证明），因此其他人获得的钱就很少了。

事实上，设第一个人红包金额为`m`，红包均值为`a`，则`m`服从这个式子：

$$
0.01 \leq m \leq 2a
$$
这就能看出他的错误了，没有限定条件

以上是我的分析，如有错误欢迎指出。