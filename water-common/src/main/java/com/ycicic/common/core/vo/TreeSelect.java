package com.ycicic.common.core.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ycicic
 */
@Data
public class TreeSelect {

    private Long id;

    private String label;

    private List<TreeSelect> children;

}
