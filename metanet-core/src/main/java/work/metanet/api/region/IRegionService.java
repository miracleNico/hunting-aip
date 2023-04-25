package work.metanet.api.region;

import cn.hutool.json.JSONArray;

public interface IRegionService {
	
	JSONArray loadAllRegion() throws Exception;
	
}
