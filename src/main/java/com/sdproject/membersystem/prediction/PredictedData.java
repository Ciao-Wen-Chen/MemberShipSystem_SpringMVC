package com.sdproject.membersystem.prediction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PredictedData {
    private final String matchLevel;
    private final String matchName;
    private final String matchSurface;

    private final String p1;
    private final int p1Age;
    private final int p1Fatigue;
    private final String p1Hand;
    private final int p1Height;
    private final int p1Id;
    private final int p1Rank;
    private final int p1Point;

    private final String p2;
    private final int p2Age;
    private final int p2Fatigue;
    private final String p2Hand;
    private final int p2Height;
    private final int p2Id;
    private final int p2Rank;
    private final int p2Point;
}
