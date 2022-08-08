package com.sdproject.membersystem.prediction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReturnData {
    private String p1Name;
    private String p2Name;
    private String p1WinPercentage;
    private String p2WinPercentage;
}
