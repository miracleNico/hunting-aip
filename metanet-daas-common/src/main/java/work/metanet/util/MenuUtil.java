package work.metanet.util;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import work.metanet.api.permission.vo.MenuVo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class MenuUtil {

	public static String jsonMenus(List<MenuVo> menuList,String menuFilePath) throws Exception {
		TreeNodeConfig treeNodeConfig = TreeNodeConfig.DEFAULT_CONFIG.setIdKey("id").setNameKey("title").setParentIdKey("parentId").setChildrenKey("child");
		List<Tree<String>> trees = TreeUtil.build(menuList, "", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setName(treeNode.getTitle());
            tree.putExtra("href", treeNode.getHref());
            tree.putExtra("icon", treeNode.getIcon());
            tree.putExtra("target", treeNode.getTarget());
        });
		JSONObject jsonObject = JSONUtil.readJSONObject(FileUtil.file(menuFilePath), Charset.forName("UTF-8"));
		jsonObject.getConfig().setOrder(true);
		//LinkedHashMap-按固定顺序排列
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < trees.size(); i++) {
			map.put(Convert.toStr(i), trees.get(i));			
		}
		jsonObject.set("menuInfo", map);
		return JSONUtil.toJsonStr(jsonObject);
	}
	
}
