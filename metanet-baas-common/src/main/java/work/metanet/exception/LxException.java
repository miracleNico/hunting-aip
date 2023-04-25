package work.metanet.exception;

import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class LxException extends Exception{
	
	private static final long serialVersionUID = -2478242846589278618L;
	
	private Result<Object> result = ResultMessage.FAILURE_CUSTOM.result();
	
	public LxException setMsg(String msg) {
		this.result.setMsg(msg);
		return this;
	}
	
	public LxException setData(Object msg) {
		this.result.setData(msg);
		return this;
	}
    
}
