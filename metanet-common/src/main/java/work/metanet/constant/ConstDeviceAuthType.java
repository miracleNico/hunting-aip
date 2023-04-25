package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 设备认证方式
 * @Author wanbo
 * @DateTime 2020/07/10
 */
@AllArgsConstructor
@Getter
public enum ConstDeviceAuthType {
	
	MAC("mac认证"),SN("序列号认证"),MACSN("mac+序列号认证")
	;
	
	private String text;
	
}
