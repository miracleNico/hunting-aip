package work.metanet.api.vision.protocol;

import com.fasterxml.jackson.annotation.JsonFormat;
import work.metanet.base.Paging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "请求-计划列表")
public class ReqVisionPlanList extends Paging implements Serializable {
    private static final long serialVersionUID = 6912222101768302948L;

    @ApiModelProperty(value = "用户序列号",example = "2734086da3a945d5baef67b1f5f0041f")
    private String userId;

    @ApiModelProperty(value = "计划序列号" , example = "878c068db6da11eb8b765254006d7470")
    private String planId;

    @ApiModelProperty(value = "提醒类型（0年提醒,1月提醒,2周提醒,3天提醒,4小时提醒,5一次性提醒,6每45分钟提醒,7每30分钟提醒,8每3天提醒）" , example = "5")
    private int planType;

    @ApiModelProperty(value = "执行模式（0状态栏提醒,1弹出框提醒,2立即强制切换,3倒计时切换可取消）" ,example = "3")
    private int actionMode;

    @ApiModelProperty(value = "计划操作（0视力测试,1视力防护,2其他）" , example = "0")
    private int planAction;

    @ApiModelProperty(value = "計劃名稱")
    private String planName;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @ApiModelProperty(value = "计划开始时间，执行计划所规定的活动的开始时间，也是首次提醒的时间点", example = "2021-05-01")
    private Date startDate;

    /**
     * 计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @ApiModelProperty(value = "计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间", example = "2021-05-01")
    private Date endDate;

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "响应-计划列表")
    public static class RespVisionPlanList extends ReqVisionPlanInfo.RespVisionPlanInfo implements Serializable{
        private static final long serialVersionUID = 2345678896790L;
    }

}

