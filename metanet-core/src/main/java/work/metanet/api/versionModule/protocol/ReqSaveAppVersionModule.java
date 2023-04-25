package work.metanet.api.versionModule.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveAppVersionModule implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String versionModuleId;
	private String versionId;
	private String moduleName;
	private String callType;
	private String parentId;
	private String packageName;
	private String className;
	private String parameter;
	private String moduleIcon;
	private String businessAppIds;
	private Integer sort;
    private String remark;
    

}
