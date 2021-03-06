package org.ff4j.spring.boot.resources;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ff4j.spring.boot.constants.CommonConstants;
import org.ff4j.spring.boot.domain.AuthorizationsManagerApiBean;
import org.ff4j.spring.boot.domain.FF4jStatusApiBean;
import org.ff4j.spring.boot.services.FF4jServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.ff4j.web.FF4jWebConstants.*;
import static org.ff4j.spring.boot.constants.FeatureConstants.PATH_PARAM_UID;
import static org.ff4j.spring.boot.constants.FeatureConstants.RESOURCE_FF4J;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RestController
@RequestMapping(value = RESOURCE_FF4J)
public class FF4jResource {
    @Autowired
    private FF4jServices ff4JServices;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Gets <b>ff4j</b> status overview",
            notes = "Gets information related to <b>Monitoring</b>, <b>Security</b>, <b>Cache</b> and <b>Store</b>")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Success, return status of ff4j instance", response = FF4jStatusApiBean.class))
    public FF4jStatusApiBean getStatus() {
        return ff4JServices.getStatus();
    }

    @RequestMapping(value = CommonConstants.ROOT + RESOURCE_SECURITY, method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets <b>Security</b> information (permissions manager)",
            notes = "Security is implemented through dedicated <b>AuthorizationsManager</b> but it's not mandatory")
    @ApiResponses({@ApiResponse(code = 200, message = "Status of current ff4j security bean", response = AuthorizationsManagerApiBean.class),
            @ApiResponse(code = 404, message = "no security has been defined")})
    public AuthorizationsManagerApiBean getSecurityInfo() {
        return ff4JServices.getSecurityInfo();
    }


    @RequestMapping(value = CommonConstants.ROOT + OPERATION_CHECK + CommonConstants.ROOT + PATH_PARAM_UID, method = GET)
    @ApiOperation(value = "<b>Simple check</b> feature toggle", response = Boolean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "If feature is flipped"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity<Boolean> check(@PathVariable(value = PARAM_UID) String featureUID) {
        Boolean status = ff4JServices.check(featureUID);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_CHECK + CommonConstants.ROOT + PATH_PARAM_UID, method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "<b>Advanced check</b> feature toggle (parameterized)", response = Boolean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "If feature is flipped"),
            @ApiResponse(code = 400, message = "Invalid parameter"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity<Boolean> check(@PathVariable(value = PARAM_UID) String featureUID, @RequestParam MultiValueMap<String, String> formParams) {
        Map<String, String> map = formParams.toSingleValueMap();
        Boolean status = ff4JServices.check(featureUID, map);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
