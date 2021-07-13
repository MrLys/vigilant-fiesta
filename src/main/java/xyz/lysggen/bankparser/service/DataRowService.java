package xyz.lysggen.bankparser.service;

import xyz.lysggen.bankparser.model.Category;
import xyz.lysggen.bankparser.model.DataRow;

public class DataRowService {

    public void assignCategory(DataRow row, Category category) {
        row.setCategory(category);
    }
}
