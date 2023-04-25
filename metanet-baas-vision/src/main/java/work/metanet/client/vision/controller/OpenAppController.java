package work.metanet.client.vision.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags={"应用"})
@RestController
@RequestMapping("api/app")
public class OpenAppController extends BaseController {

}
