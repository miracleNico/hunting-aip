# RESTful API调试方法


## 1、查看接口文档

大型项目事先会设计内部接口和对外接口，文档单独输出，通常采用doc格式。
小型项目一般不单独给出doc文档，而直接做好后台系统后输出swagger文档。
本项目的swagger文档地址为：
http://ip:port/doc.html

视力检测的接口调用顺序：
- 第三方用户同步/api/userEye/syncUser：传入第三方系统的用户信息，自动创建新用户和两只眼睛信息，返回信息里面含有三个ID。所以保存眼睛信息接口就不用调用了
- 眼睛信息操作有三个：保存眼睛信息接口自动判断eyeid是否存在，存在则修改，否则新增。用户眼睛信息查询接口根据eyeid查询，而获取眼睛信息列表接口则通过userid查询多条
- 视力测试活动操作有三个：保存视力测试活动接口自动判断testid是否存在，存在则修改，否则新增。查询接口根据testid查询，而列表接口则通过eyeid查询多条
- 视力报告操作有三个：保存接口自动判断reportid是否存在，存在则修改，否则新增。查询接口根据reportid查询，而列表接口则通过testid查询多条
- 提供搜索接口：视力报告搜索接口通过关键字搜索报告建议字段，还可以通过第三方系统的用户id来搜索，也提供通过本系统userid和测试模式（自测模式或者家长模式）来搜索
- 提供推荐接口：视力报告推荐接口通过设备类型、诊断结果、最大年龄和最小年龄、随机等来自动推荐视力报告

## 2、使用POSTMAN或者其他RESTful API客户端调试
- （1）启动本服务后，本服务的侦听端口为8886或者8887
- （2）启动Postman，在Collections里面新增一个collection，命名为vision
- （3）接下来，点击“New”新增一个Request，输入Request Name（假设调试同步用户接口，推荐输入syncUser）保存
- （4）在“Enter request URL”中输入接口地址，例如：http://127.0.0.1:8886/api/userEye/syncUser
- （5）修改接口地址前面的操作类型：GET，POST，等，对于SyncUser应该选择POST
- （6）设置请求参数，对于GET操作，应该在Params里面设置，而对于POST操作，应该在Body里面设置，例如，对于SyncUser：
在Body里面录入：

```
{
 "appID": "c01b21b4eec248e599b950caf8031ea7",
 "ownerAge": 16,
 "ownerHeight": 170,
 "ownerId": "1234567890",
 "ownerName": "Edison Feng",
 "ownerTel": "13590909090"
}
```

（7）后面需要检查，认证和ip包头，例如认证采用：Bearer Token，则录入token
对于SyncUser暂时关闭了认证和header，因此不需要输入Authorization和Headers

## 3、Android
使用成熟的RESTful API客户端开源包，例如：okgo 3.0.4


```
    public static void postBodyNoToken(String url, Map<String, Object> params, final BaseCallback callback) {
        final RequestBody requestBody = RequestBody.create(TYPE_JSON, params == null ? "" : mapToJson(params));
        String deviceId = SPUtils.getInstance(SPConstant.SHAPE_PUBLIC).getString(SPConstant.KEY_PUBLIC_DEVICE_CODE);
        String cid = SPUtils.getInstance(SPConstant.SHAPE_PUBLIC).getString(SPConstant.KEY_PUBLIC_CID);
        String nonce = String.valueOf((System.currentTimeMillis() / 10000));
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = EncryptUtil.luoxiEncrypt(params, nonce, timeStamp);
        OkGo.<String>post(url)
                .upRequestBody(requestBody)
                .headers("deviceId", deviceId)
                .headers("appId", LuoxiKeyUtil.getLuoxiAppId())
                .headers("timestamp", timeStamp)
                .headers("nonce", nonce)
                .headers("cid", cid)
                .headers("sign", sign)
                .headers("versionCode", AppUtils.getAppVersionName())
                .headers("packageName", AppUtils.getAppPackageName())
                .execute(getCommonCallback(callback));
    }

    private static Callback<String> getCommonCallback(final BaseCallback callback) {
        Callback<String> commonCallback = new Callback<String>() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                if (callback != null) callback.onStart();
            }

            @Override
            public void onSuccess(Response<String> response) {
                if (response == null) return;
                //请求成功

            }

            @Override
            public void onCacheSuccess(Response<String> response) {

            }

            @Override
            public void onError(Response<String> response) {
                if (callback != null) callback.onFailed(-1, response.body());
            }

            @Override
            public void onFinish() {
                if (callback != null) callback.onFinish();
            }

            @Override
            public void uploadProgress(Progress progress) {

            }

            @Override
            public void downloadProgress(Progress progress) {

            }

            @Override
            public String convertResponse(okhttp3.Response response) throws Throwable {
                return response.body().string();
            }
        };
        return commonCallback;
    }
```

## 4、VUE

使用axios模块来发送请求，例如：对于syncUser

```
export const save = (data) => {
    return axios({
        url: '/api/userEye/syncUser',
        method: 'post',
        data
    })
}

```
然后在global.js中设置

```
export const baseUrl = 'http://localhost:8001'
```

然后在config。js中设置

```
export default {
  method: 'get',
  // 基础url前缀
  baseUrl: baseUrl,
  // 请求头信息
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  },
  // 参数
  data: {},
  // 设置超时时间
  timeout: 10000,
  // 携带凭证
  withCredentials: true,
  // 返回数据类型
  responseType: 'json'
}
```

在axios。js中设置：

```
    const instance = axios.create({
      baseURL: config.baseUrl,
      headers: config.headers,
      timeout: config.timeout,
      withCredentials: config.withCredentials
    })
```

最后，在相应页面js中引用，例如：加载用户角色信息

```
		findUserRoles: function () {
			this.$api.role.findAll().then((res) => {
				// 加载角色集合
				this.roles = res.data	
			})
		},
```
其中引用的role模块中的findAll方法如下：


```
		export const findAll = () => {
		    return axios({
		        url: '/role/findAll',
		        method: 'get'
		    })
		}
```

## 5.其他参考

[Restful接口开发测试指南](https://blog.51cto.com/u_5526964/2399162)
		