package Passed.Lab1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Ex1 {
    public static void main( String[] args ) throws IOException {
        //Секція 1 тут прописано спосіб отримання мультирядкового тексту в консолі
    System.out.println( "Введіть текст:" );
    Scanner in = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while (in.hasNextLine()) {
        String line = in.nextLine();
        if (line.isEmpty()) {
            break;
        } // else
        sb.append(line).append("\n");
    }
    String text = sb.toString();
        //Секція 2: Вилучають пробіли, цифри та знаки пунктуації та переводимо текст до верхнього регістру
    Map<Character, Float> dict = new Hashtable<>();
    String cleanText= text.replaceAll("[\\d\\s\n,./|!?;:()-_@#$%^&*\u2116A-Za-z`'’ –»«]","");
    text = cleanText.toUpperCase(Locale.ROOT);
    System.out.println("\n"+text);
        // Секція 3: Перевожу текст ініціалізую алфавіт та обраховую частотну таблицю
    char[] charArrayFromText = text.toCharArray();
    char[] alphabet = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ".toCharArray();
    int i = 0;
        //Секція 4: заповнюю словник з якого в результаті витягують дані
    for (char leter:alphabet) {
        for (char leterFromText:charArrayFromText) {
            if(leterFromText == leter)i++;
        }
        float value = (float) i /text.length();
        dict.put(leter, value);
        i = 0;
    }
    lineChart(dict);

}
    public static void lineChart( Map<Character, Float> dict) throws FileNotFoundException, IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            // Секція 5: Створення тамблиці Excel та витягування даних з словника в файл
            String sheetName = "CountryLineChart";

            XSSFSheet sheet = wb.createSheet(sheetName);
            int rowNumber = 0;
            for (Map.Entry<Character, Float> entry : dict.entrySet()) {
                Row row = sheet.createRow(rowNumber++);
                Cell keyCell = row.createCell(0);
                keyCell.setCellValue(entry.getKey().toString());
                Cell valueCell = row.createCell(1);
                valueCell.setCellValue(entry.getValue());
            }
            // Секція 6: Створення форми для гістограми
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 4, 14, 30);

            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText("частотна таблиця української мови");
            chart.setTitleOverlay(false);

            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.BOTTOM);


            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            bottomAxis.setTitle("Літери");

            // Секція 7: Витягування даних для гістограми

            XDDFDataSource<String> letter = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                    new CellRangeAddress(0, 32, 0, 0));

            XDDFNumericalDataSource<Double> count = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                    new CellRangeAddress(0, 32, 1, 1));


            // Секція 8: Заповнення гістограми

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis );

            XDDFBarChartData.Series series1 = (XDDFBarChartData.Series) data.addSeries(letter, count);
            series1.setTitle("Частота", null);



            chart.plot(data);
            //Секція 9: Відкланданя xlsx документу

            String filename = "src/main/Rsults After Generation/ChartOfUkrLanguageBasedOnText.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                wb.write(fileOut);
            }
        }
    }
}

