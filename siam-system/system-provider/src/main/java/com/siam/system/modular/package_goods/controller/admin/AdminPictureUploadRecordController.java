package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.PictureUploadRecordService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.PictureUploadRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin/pictureUploadRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台图片上传记录模块相关接口", description = "AdminPictureUploadRecordController")
public class AdminPictureUploadRecordController {

    @Autowired
    private PictureUploadRecordService pictureUploadRecordService;

    @ApiOperation(value = "图片上传记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PictureUploadRecord pictureUploadRecord){
        BasicData basicResult = new BasicData();
        Page<PictureUploadRecord> page = pictureUploadRecordService.getList(pictureUploadRecord.getPageNo(), pictureUploadRecord.getPageSize(), pictureUploadRecord);

        return BasicResult.success(page);
    }

    /*@ApiOperation(value = "图片上传记录创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片上传记录主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "图片上传记录名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "图片上传记录详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(PictureUploadRecord pictureUploadRecord){
        BasicResult basicResult = new BasicResult();

        pictureUploadRecord.setCreateTime(new Date());
        pictureUploadRecord.setUpdateTime(new Date());
        pictureUploadRecordService.insertSelective(pictureUploadRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "图片上传记录修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片上传记录主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "图片上传记录名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "图片上传记录详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "/update")
    public BasicResult update(PictureUploadRecord pictureUploadRecord){
        BasicResult basicResult = new BasicResult();

        pictureUploadRecord.setUpdateTime(new Date());
        pictureUploadRecordService.updateByPrimaryKeySelective(pictureUploadRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }*/


//    @ApiOperation(value = "图片上传记录删除")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "图片上传记录主键id集合(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "String")
//    })
//    @DeleteMapping(value = "/delete")
//    public BasicResult delete(@RequestBody @Validated(value = {}) AdminParam param  @RequestParam(value = "ids") List<Integer> ids){
//        BasicResult basicResult = new BasicResult();
//        for (Integer id : ids) {
//            pictureUploadRecordService.deleteByPrimaryKey(id);
//        }
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("删除成功");
//        return basicResult;
//    }
}