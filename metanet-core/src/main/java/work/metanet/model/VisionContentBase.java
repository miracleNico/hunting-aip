package work.metanet.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * t_eyes_article
 * @author  nicely
 */
@Data
@Accessors(chain = true)
@Table(name = "t_eyes_article")
public class VisionContentBase extends Base implements Serializable {
    /**
     * 内容ID
     */
	@Id
    @ApiModelProperty(value = "内容ID")
    private String contentId;

    /**
     * 内容名称
     */
    @ApiModelProperty(value = "内容名称")
    private String name;

    /**
     * 显示标题
     */
    @ApiModelProperty(value = "显示标题")
    private String displayTitle;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    private String digest;

    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词")
    private String keyword;

    /**
     * 行业
     */
    @ApiModelProperty(value = "行业")
    private int industry;

    /**
     * 锻炼类型
     */
    @ApiModelProperty(value = "锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他）")
    private int type1;

    /**
     * 锻炼模式
     */
    @ApiModelProperty(value = "锻炼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式）")
    private int type2;

    /**
     * 判定结果
     */
    @ApiModelProperty(value = "判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）")
    private int type3;

    /**
     * 内容来源
     */
    @ApiModelProperty(value = "来源（-1未知，0原创，1转载，2翻译，3其他）")
    private int type4;

    /**
     * 内容的H5文本
     */
    @ApiModelProperty(value = "文本内容")
    private String text;

    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    /**
     * 音频链接
     */
    @ApiModelProperty(value = "音频链接")
    private String audioUrl;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面地址")
    private String coverPictureUrl;

    /**
     * 置顶排序 置顶（整数值越小，排在前面）
     */
    @ApiModelProperty(value = "置顶（整数值越小，排在前面）")
    private int order;

    /**
     * 分享外链
     */
    @ApiModelProperty(value = "内容分享外链")
    private String externalLink;


    @ApiModelProperty(value = "是否全屏(0不全屏，1视频全屏，内容全屏 默认为0)",example = "0")
    private int isFullScreen;

    private static final long serialVersionUID = 1L;
}