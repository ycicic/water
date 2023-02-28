package com.ycicic.framework.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ycicic.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 自动填充实现
 *
 * @author ycicic
 */
@Configuration
public class WaterMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getUserId();
        strictInsertFill(metaObject, "createBy", Long.class, userId);
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "updateBy", Long.class, userId);
        strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getUserId();
        strictUpdateFill(metaObject, "updateBy", Long.class, userId);
        strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }

    private Long getUserId() {
        try {
            return SecurityUtils.getLoginUserId();
        } catch (Exception e) {
            return -1L;
        }
    }

}
