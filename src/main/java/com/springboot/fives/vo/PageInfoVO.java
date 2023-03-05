package com.springboot.fives.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class PageInfoVO {
    @Builder.Default private int startCount=0;
    @Builder.Default private int pageViewCount=5;
    @Builder.Default private int bundleCount=5;
    @Builder.Default private int totalCount = 0;
    
    private String query;

}
