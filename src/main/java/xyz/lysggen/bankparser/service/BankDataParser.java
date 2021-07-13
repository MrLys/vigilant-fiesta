package xyz.lysggen.bankparser.service;

import org.springframework.stereotype.Service;
import xyz.lysggen.bankparser.model.BankStatement;
import xyz.lysggen.bankparser.model.DataRow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BankDataParser {
    private static final Pattern bankAccount = Pattern.compile("");
    private static final Pattern period = Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{5}) i perioden (\\d{2}\\.\\d{2}\\.\\d{4}) - (\\d{2}\\.\\d{2}\\.\\d{4}) (.*)");
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    public BankStatement readData(String filename) {
        BankStatement bankStatement = new BankStatement();
        List<DataRow> rows = new ArrayList<>();
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(filename));){
            String current = tsvReader.readLine();
            boolean hasFoundTable = false;
            boolean hasFoundAccount = false;
            while (current != null) {
                String lcCurrent = current.toLowerCase();
                if (lcCurrent.contains("kontoutskrift")) {
                    if (hasFoundAccount) {
                        current = tsvReader.readLine();
                        continue;
                    }
                    bankStatement = parseBankStatement(lcCurrent);
                    if (bankStatement != null) {
                        hasFoundAccount = true;
                    }
                    // skip
                } else if (lcCurrent.contains("forklaring")) {
                    hasFoundTable = true;
                } else if (lcCurrent.contains("saldo i dykkar")) {
                    break;
                } else if (lcCurrent.contains("overført til neste side")) {
                    hasFoundTable = false;
                } else if (lcCurrent.contains("overført frå forrige side")) {
                    //skip
                } else if (hasFoundTable) {
                    rows.add(parseRow(formatRow(lcCurrent)));
                }
                current = tsvReader.readLine();
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(rows);
        bankStatement.setDataRows(rows);
        return bankStatement;
    }
    public BankStatement parseBankStatement(String line) {
        BankStatement bankStatement = null;
        Matcher matcher = period.matcher(line);
        if (matcher.find()) {
            bankStatement = new BankStatement();
            bankStatement.setAccount(matcher.group(1));
            try {
                bankStatement.setStartDate(formatter.parse(matcher.group(2)));
            } catch (ParseException e) {
                bankStatement.setStartDate(null);
            }
            try {
                bankStatement.setEndDate(formatter.parse(matcher.group(3)));
            } catch (ParseException e) {
                bankStatement.setEndDate(null);
            }
            bankStatement.setName(matcher.group(4));
            System.out.println(bankStatement.getAccount());
            System.out.println(bankStatement.getStartDate());
            System.out.println(bankStatement.getEndDate());
            System.out.println(bankStatement.getName());
        }
        return bankStatement;
    }
    public String formatRow(String rowData) {
        if (rowData == null || rowData.length() == 0) {
            return null;
        }
        // assumes row is on the correct format.
        char[] characters = rowData.toCharArray();
        StringBuilder rowBuilder = new StringBuilder("\"");
        int i = findNextElement(characters, 0);
        i = parseRowElement(rowBuilder, characters, i);
        rowBuilder.append("\",\"");
        i = findNextElement(characters, i);
        i = parseRowElement(rowBuilder, characters, i);
        rowBuilder.append("\",\"");
        //now we the amountIn might be empty, so we need to account for that.
        int newI = findNextElement(characters, i);
        if ((newI-i) > (10 + 14)) {
           // empty
            rowBuilder.append("0\",\"");
            i = newI;
            i = parseRowElement(rowBuilder, characters, i);
        } else {
            i = newI;
            i = parseRowElement(rowBuilder, characters, i);
            rowBuilder.append("\",\"0");
        }
        rowBuilder.append("\",\"");
        i = findNextElement(characters, i);
        i = parseRowElement(rowBuilder, characters, i);
        rowBuilder.append("\"");
        return rowBuilder.toString();
    }


    public int parseRowElement(StringBuilder descBuilder, char[] characters, int i) {
        boolean looking = true;
        while (i < characters.length) {
            if (characters[i] == '\n') {
                return i;
            }
            if (characters[i] != ' ') {
                descBuilder.append(characters[i]);
            } else {
                if (characters[i+1] == ' ') {
                    return i;
                } else {
                    descBuilder.append(characters[i]);
                }
            }
            i++;
        }
       return i;
    }

    public int findNextElement(char[] characters, int i) {
        while (i < characters.length  && characters[i] == ' ') {
            i++;
        }
        return i;
    }

    public DataRow parseRow(String data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if (data.length() == 0) {
            return null;
        }
        String[] d = data.split(",\"");
        if (d.length != 5) {
            return null;
        }
        DataRow row = new DataRow();
        row.setDescription(d[0].replace("\"",""));
        row.setDate(d[1].replace("\"",""));
        row.setAmountIn(parseDouble(d[2]));
        row.setAmountOut(parseDouble(d[3]));
        row.setPostedDate(d[4].replace("\"",""));
        return row;
    }
    public double parseDouble(String number) {
        number = number.replace(".","");
        number = number.replace(",",".");
        number = number.replace("\"","");
        return Double.parseDouble(number);
    }
}
