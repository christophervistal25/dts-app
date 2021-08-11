package www.seotoolzz.com.dts.Repositories;

import www.seotoolzz.com.dts.Database.Models.Office;

public class OfficeRepository {
    public static Office make(String office_code, String office_name, String office_short_name)
    {
        Office office = new Office();
        office.setOffice_code(office_code);
        office.setOffice_name(office_name);
        office.setOffice_short_name(office_short_name);
        return office;
    }
}
