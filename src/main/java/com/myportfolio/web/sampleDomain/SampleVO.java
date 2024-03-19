package com.myportfolio.web.sampleDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO {
    private Integer mno;
    private String firstName;
    private String lastName;
}
