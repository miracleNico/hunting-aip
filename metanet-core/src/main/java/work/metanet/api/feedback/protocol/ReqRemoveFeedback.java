package work.metanet.api.feedback.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveFeedback implements Serializable{
	
	private static final long serialVersionUID = 1055722802402768004L;

	@NotBlank(message = "反馈id不能为空")
	private String feedbackId;
	
}
