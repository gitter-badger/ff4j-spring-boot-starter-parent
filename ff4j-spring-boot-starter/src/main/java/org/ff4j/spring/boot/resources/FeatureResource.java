package org.ff4j.spring.boot.resources;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ff4j.spring.boot.constants.CommonConstants;
import org.ff4j.spring.boot.constants.FeatureConstants;
import org.ff4j.spring.boot.domain.FeatureApiBean;
import org.ff4j.spring.boot.model.FeatureActions;
import org.ff4j.spring.boot.services.FeatureServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.ff4j.web.FF4jWebConstants.*;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RestController
@RequestMapping(value = FeatureConstants.RESOURCE_FF4J_STORE_FEATURES + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_UID)
public class FeatureResource {

    @Autowired
    private FeatureServices featureService;


    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public FeatureApiBean getFeatureByUID(@PathVariable(value = PARAM_UID) String featureUID) {
        return featureService.getFeature(featureUID);
    }

    @RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or update a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Feature uid is blank (or) feature uid did not match with the requested feature uid to be created or updated"),
            @ApiResponse(code = 201, message = "Feature has been created"),
            @ApiResponse(code = 202, message = "Feature has been updated"),
            @ApiResponse(code = 204, message = "No content, no changes made to the feature")})
    public ResponseEntity<Boolean> createOrUpdateFeature(@PathVariable(value = PARAM_UID) String featureUID, @RequestBody FeatureApiBean featureApiBean) {
        return FeatureActions.getBooleanResponseEntityByHttpStatus(featureService.createOrUpdateFeature(featureUID, featureApiBean));
    }

    @RequestMapping(method = DELETE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "No content, feature is deleted"),
            @ApiResponse(code = 404, message = "Feature not found")
    })
    public ResponseEntity deleteFeature(@PathVariable(value = PARAM_UID) String featureUID) {
        featureService.deleteFeature(featureUID);
        return new ResponseEntity(NO_CONTENT);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_ENABLE, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enable a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Features has been enabled"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity enableFeature(@PathVariable(value = PARAM_UID) String featureUID) {
        featureService.enableFeature(featureUID);
        return new ResponseEntity(ACCEPTED);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_DISABLE, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Disable a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Features has been disabled"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity disableFeature(@PathVariable(value = PARAM_UID) String featureUID) {
        featureService.disableFeature(featureUID);
        return new ResponseEntity(ACCEPTED);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_GRANTROLE + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_ROLE, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Grant a permission to a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Permission has been granted"),
            @ApiResponse(code = 404, message = "Feature not found"),
            @ApiResponse(code = 304, message = "Role already exists, nothing to update")})
    public ResponseEntity grantRoleToFeature(@PathVariable(value = PARAM_UID) String featureUID, @PathVariable(value = FeatureConstants.PARAM_ROLE) String role) {
        featureService.grantRoleToFeature(featureUID, role);
        return new ResponseEntity<>(ACCEPTED);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_REMOVEROLE + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_ROLE, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove a permission from a feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Permission has been granted"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity removeRoleFromFeature(@PathVariable(value = PARAM_UID) String featureUID, @PathVariable(value = FeatureConstants.PARAM_ROLE) String role) {
        featureService.removeRoleFromFeature(featureUID, role);
        return new ResponseEntity<>(ACCEPTED);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_ADDGROUP + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_GROUP, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Define the group of the feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Group has been defined"),
            @ApiResponse(code = 404, message = "Feature not found"),
            @ApiResponse(code = 304, message = "Group already exists, nothing to update")})
    public ResponseEntity addGroupToFeature(@PathVariable(value = PARAM_UID) String featureUID, @PathVariable(value = FeatureConstants.PARAM_GROUP) String groupName) {
        featureService.addGroupToFeature(featureUID, groupName);
        return new ResponseEntity<>(ACCEPTED);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_REMOVEGROUP + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_GROUP, method = POST, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove the group of the feature", response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Group has been removed"),
            @ApiResponse(code = 404, message = "Feature not found")})
    public ResponseEntity removeGroupFromFeature(@PathVariable(value = PARAM_UID) String featureUID, @PathVariable(value = FeatureConstants.PARAM_GROUP) String groupName) {
        featureService.removeGroupFromFeature(featureUID, groupName);
        return new ResponseEntity<>(ACCEPTED);
    }
}
