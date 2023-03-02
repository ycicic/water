package com.ycicic.common.core.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "分页查询参数")
public class PageParam {

    @ApiModelProperty("当前页数")
    private Integer currentPage = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    public <T> Page<T> getPage(Class<T> clazz) {
        return new Page<>(currentPage, pageSize);
    }

    public Page<Object> getPage() {
        return new Page<>(currentPage, pageSize);
    }

}
