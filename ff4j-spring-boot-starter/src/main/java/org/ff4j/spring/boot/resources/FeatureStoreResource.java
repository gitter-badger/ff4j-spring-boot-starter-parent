package org.ff4j.spring.boot.resources;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ff4j.spring.boot.constants.CommonConstants;
import org.ff4j.spring.boot.constants.FeatureConstants;
import org.ff4j.spring.boot.domain.CacheApiBean;
import org.ff4j.spring.boot.domain.FeatureApiBean;
import org.ff4j.spring.boot.domain.FeatureStoreApiBean;
import org.ff4j.spring.boot.services.FeatureStoreServices;
import org.ff4j.spring.boot.domain.GroupDescApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.ff4j.web.FF4jWebConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RestController
@RequestMapping(value = FeatureConstants.RESOURCE_FF4J_STORE)
public class FeatureStoreResource {
    @Autowired
    private FeatureStoreServices featureStoreService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Displays information regarding the <b>FeaturesStore</b>",
            response = FeatureStoreApiBean.class)
    @ApiResponses(@ApiResponse(code = 200, message = "status of current feature store", response = FeatureStoreApiBean.class))
    public FeatureStoreApiBean getFeatureStore() {
        return featureStoreService.getFeatureStore();
    }

    @RequestMapping(value = CommonConstants.ROOT + RESOURCE_FEATURES, method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Displays all the <b>Features</b>", response = FeatureApiBean.class)
    @ApiResponses(@ApiResponse(code = 200, message = "get all features"))
    public Collection<FeatureApiBean> getAllFeatures() {
        return featureStoreService.getAllFeatures();
    }

    @RequestMapping(value = CommonConstants.ROOT + RESOURCE_GROUPS, method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Display information regarding <b>Groups</b>", response = GroupDescApiBean.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Groups resource", response = GroupDescApiBean.class)})
    public Collection<GroupDescApiBean> getAllGroups() {
        return featureStoreService.getAllGroups();
    }

    @RequestMapping(value = CommonConstants.ROOT + STORE_CLEAR, method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete all <b>Features</b> in store")
    @ApiResponses(@ApiResponse(code = 204, message = "all feature have been deleted"))
    public ResponseEntity deleteAllFeatures() {
        featureStoreService.deleteAllFeatures();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = CommonConstants.ROOT + RESOURCE_CACHE, method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Display information related to <b>Cache</b>")
    @ApiResponses({@ApiResponse(code = 200, message = "Gets the cached features", response = CacheApiBean.class),
            @ApiResponse(code = 404, message = "feature store is not cached")})
    public CacheApiBean getFeaturesFromCache() {
        return featureStoreService.getFeaturesFromCache();
    }

    @RequestMapping(value = CommonConstants.ROOT + FeatureConstants.RESOURCE_CLEAR_CACHE, method = RequestMethod.DELETE)
    @ApiOperation(value = "Clear cache", response = ResponseEntity.class)
    @ApiResponses({@ApiResponse(code = 204, message = "cache is cleared"),
            @ApiResponse(code = 404, message = "feature store is not cached")})
    public ResponseEntity clearCachedFeatureStore() {
        featureStoreService.clearCachedFeatureStore();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
