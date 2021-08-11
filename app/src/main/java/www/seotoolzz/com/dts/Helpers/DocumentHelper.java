package www.seotoolzz.com.dts.Helpers;

import java.util.regex.Pattern;

public class DocumentHelper {
    public final String DOCUMENT_DATA_SEPARATOR = "|";
    public final String SEPERATOR_PATTERN = "\\|";
    public final String DOCUMENT_REFERENCE_PATTERN = "\\d+-\\d+-\\d+-\\d+";

    public static final int REFERENCE_NO_INDEX          = 0;
    public static final int OFFICE_INDEX                = 1;
    public static final int CLAIMANT_INDEX              = 2;
    public static final int TRANSACTION_INDEX           = 3;
    public static final int CURRENT_DEPARTMENT_INDEX    = 4;
    public static final int CURRENT_STATION_INDEX       = 5;
    public static final int LOGS_USER_ID_INDEX          = 6;
    public static final int HISTORY_LOGS_DATETIME_INDEX = 7;
    public static final int CHARGE_TO_INDEX             = 8;
    public static final int STATUS_INDEX               = 9;


    public boolean isReferenceNo(String data)
    {
        return Pattern.matches(this.DOCUMENT_REFERENCE_PATTERN, data);
    }


    public String[] extract(String data)
    {
        return data.split(this.SEPERATOR_PATTERN);
    }

    public String extract(String data, int index)
    {
        return data.split(this.SEPERATOR_PATTERN)[index];
    }

}
