package xyz.lysggen.bankparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.lysggen.bankparser.model.BankStatement;
import xyz.lysggen.bankparser.model.Transaction;
import xyz.lysggen.bankparser.service.BankDataParser;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@SpringBootTest
class BankparserApplicationTests {
	@Autowired
	private BankDataParser bankDataParser;
	@Test
	void contextLoads() {
	}
	@Test
	public void readDataTest() {
		Assertions.assertThrows(NullPointerException.class, () -> bankDataParser.readData((String) null));
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		Assertions.assertNull(bankDataParser.readData(new String(array, StandardCharsets.UTF_8)));
		BankStatement bankStatement = bankDataParser.readData("src/main/resources/static/output.txt");
		Assertions.assertNotNull(bankStatement);
		Assertions.assertNotNull(bankStatement.getTransactions());
	}
	@Test
	public void parseBankStatementTest() {
		String line = "           Kontoutskrift nr. 6 for konto 3705.16.49797 i perioden 01.06.2021 - 30.06.2021 Brukskonto\n";
		BankStatement bankStatement = bankDataParser.parseBankStatement(line);
		Assertions.assertNotNull(bankStatement);
	}
	@Test
	public void formatRowTest() {
		Assertions.assertNull(bankDataParser.formatRow(null));
		Assertions.assertNull(bankDataParser.formatRow(""));
		String row = "         Straksoverføring Fra: navn1 navn2 navn3                                          0101                                   660,00      0101\n";
		String formattedRow = bankDataParser.formatRow(row);
		Assertions.assertEquals("\"Straksoverføring Fra: navn1 navn2 navn3\",\"0101\",\"0\",\"660,00\",\"0101\"", formattedRow);
		row = "          Nettgiro til: Bank X Betalt: 02.01.19                                         0101               2.132,30                        0201\n";
		formattedRow = bankDataParser.formatRow(row);
		Assertions.assertEquals("\"Nettgiro til: Bank X Betalt: 02.01.19\",\"0101\",\"2.132,30\",\"0\",\"0201\"", formattedRow);
	}
	@Test
	public void parseRowTest() {
		Assertions.assertThrows(NullPointerException.class, () -> bankDataParser.parseRow(null) );
		Assertions.assertNull(bankDataParser.parseRow(""));
		Assertions.assertNull(bankDataParser.parseRow("1	2	3"));
		Transaction row = bankDataParser.parseRow("\"Hello\",\"0102\",\"3231\",\"0\",\"0102\"");
		Assertions.assertNotNull(row);
		Assertions.assertEquals("Hello", row.getDescription());
		Assertions.assertEquals("0102",row.getDate());
		Assertions.assertEquals(3231.0, row.getAmountIn());
		Assertions.assertEquals(0, row.getAmountOut());
		Assertions.assertEquals("0102", row.getPostedDate());
		row = bankDataParser.parseRow("\"Hello\",\"0102\",\"3231,20\",\"0\",\"0102\"");
		Assertions.assertNotNull(row);
		Assertions.assertEquals("Hello", row.getDescription());
		Assertions.assertEquals("0102",row.getDate());
		Assertions.assertEquals(3231.20, row.getAmountIn());
		Assertions.assertEquals(0, row.getAmountOut());
		Assertions.assertEquals("0102", row.getPostedDate());

	}

	@Test
	public void parseDescriptionTest() {
		Assertions.assertThrows(NullPointerException.class, () -> bankDataParser.parseRowElement(null,null, 9));
		String row = "         Straksoverføring Fra: navn1 navn2 navn3                                          0101                                   660,00      0101\n";
		String description = "Straksoverføring Fra: navn1 navn2 navn3";
		int i = description.length() + 9;
		StringBuilder builder = new StringBuilder();
		int retI = bankDataParser.parseRowElement(builder,row.toCharArray(), 9);
		Assertions.assertEquals(builder.toString(), description);
		Assertions.assertEquals(i, retI);
	}

	@Test
	public void parseDateTest() {
		Assertions.assertThrows(NullPointerException.class, () -> bankDataParser.parseRowElement(null,null, 9));
		String row = "         Straksoverføring Fra: navn1 navn2 navn3                                          0101                                   660,00      0101\n";
		String date = "0101";
		int i = "         Straksoverføring Fra: navn1 navn2 navn3                                          ".length() + date.length();
		StringBuilder builder = new StringBuilder();
		int afterDescI = bankDataParser.parseRowElement(new StringBuilder(), row.toCharArray(),9);
		int beforeDateI = bankDataParser.findNextElement(row.toCharArray(), afterDescI);
		Assertions.assertEquals(90, beforeDateI);
		int retI = bankDataParser.parseRowElement(builder, row.toCharArray(), beforeDateI);
		Assertions.assertEquals(builder.toString(), date);
		Assertions.assertEquals(i, retI);

	}
}
