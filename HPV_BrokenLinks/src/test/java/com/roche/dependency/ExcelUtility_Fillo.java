package com.roche.dependency;

import java.util.ArrayList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtility_Fillo {

	// Set Excel File path and sheet
	public String filePath = All_constants.EXCEL_PATH;
	public String sheet = All_constants.sheetName;
	public String fileName = All_constants.fileName;

	// list to hold the data
	// public static java.util.List<List_TestData> l1 = new
	// ArrayList<List_TestData>();

	@SuppressWarnings("deprecation")
	public Object[][] DataRead() throws FilloException {

		Object[][] ExcelData = null;

		Fillo fillo = new Fillo();
		try {
			Connection connection = fillo.getConnection(filePath + "//" + fileName);
			String query = "Select * from " + sheet;
			Recordset record = connection.executeQuery(query);
			int ROWS = record.getCount();
			ArrayList<String> arr = record.getFieldNames();
			int COLUMNS = arr.size();
			ExcelData = new Object[ROWS][COLUMNS];
			System.out.println("ROW " + ROWS + "  COL  " + COLUMNS);
			record.moveFirst();

			for (int i = 0; i < ROWS; i++) {
				ExcelData[i][COLUMNS - 1] = record.getField("Test URL List");
				if (record.hasNext()) {
					record.moveNext();
				}

			}

			// close the connection
			record.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return ExcelData;
	}

}
