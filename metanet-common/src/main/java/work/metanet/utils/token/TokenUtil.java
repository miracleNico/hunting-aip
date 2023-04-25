package work.metanet.utils.token;

import cn.hutool.core.convert.Convert;

public class TokenUtil {
	
	public static String generateToken(String sub,Long duration) {
		return JwtUtil.buildJWT(sub,Convert.toInt(duration));
	}
	
	public static JwtPayload getPayload(String claimsJws) throws Exception{
		return JwtUtil.getPayload(claimsJws);
	}
	
	public static String getPayloadSub(String claimsJws) throws Exception{
		return getPayload(claimsJws).getSub();
	}
	
	public static String getPayloadJti(String claimsJws) throws Exception{
		return getPayload(claimsJws).getJti();
	}
	
	public static Long getPayloadIat(String claimsJws) throws Exception{
		return getPayload(claimsJws).getIat();
	}
	
	public static Long getPayloadExp(String claimsJws) throws Exception{
		return getPayload(claimsJws).getExp();
	}
	
}
