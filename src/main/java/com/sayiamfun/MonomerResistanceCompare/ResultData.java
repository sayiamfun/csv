package com.sayiamfun.MonomerResistanceCompare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData {

    private String vin;
    private Long startTime;
    private Long endTime;

    private String chargeMaxSubV;
    private String chargeMaxSubVSOC;
    private String chargeMaxSubVI;

    private String runMaxSubV;
    private String runMaxSubVSOC;
    private String runMaxSubVI;

    private String chargeAvargeSubV;

    private String chargeMinSubV;
    private String chargeMinSubVSOC;
    private String chargeMinSubVI;

    private String runMinSubV;
    private String runMinSubVSOC;
    private String runMinSubVI;

    private String runAvargeSubV;

    private String monList;

    @Override
    public String toString() {
        return vin + "," +
                startTime + "," +
                endTime + "," +
                getThreeString(chargeMaxSubV) + "," +
                chargeMaxSubVSOC + "," +
                getThreeString(chargeMaxSubVI) + "," +
                getThreeString(chargeMinSubV) + "," +
                chargeMinSubVSOC + "," +
                getThreeString(chargeMinSubVI) + "," +
                getThreeString(chargeAvargeSubV) + "," +
                getThreeString(runMaxSubV) + "," +
                runMaxSubVSOC + "," +
                getThreeString(runMaxSubVI) + "," +
                getThreeString(runMinSubV) + "," +
                runMinSubVSOC + "," +
                getThreeString(runMinSubVI) + "," +
                getThreeString(runAvargeSubV) + "," +
                monList;
    }

    private String getThreeString(String value) {
        if (StringUtils.isEmpty(value)) return "null";
        return new BigDecimal(value).divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP).toString();
    }
}
