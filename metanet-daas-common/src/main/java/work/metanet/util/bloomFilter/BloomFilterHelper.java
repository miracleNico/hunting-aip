package work.metanet.util.bloomFilter;
import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

public class BloomFilterHelper <T> {

    private int numHashFunctions;
    private int bitSize;
    private Funnel<T> funnel;
    
    /**
     * @param funnel
     * @param expectedInsertions 预计插入量
     * @param fpp 可接受的错误率
     */
    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }
    
    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
    
    
    /**
 	在应用的实现类中时候：
	//预计插入量    100
	//可接受的错误率 0.01  
	private BloomFilterHelper<String> orderBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 100 , 0.01);

	//看订单是否包含在这个过滤器中
	redisService.includeByBloomFilter(orderBloomFilterHelper, "order", orderDetailDO.getOrderId())；

	//将订单Id增加到过滤器中
	redisService.addByBloomFilter(orderBloomFilterHelper, "order", orderDetailDO.getOrderId());
     */

}
