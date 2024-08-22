package core.basesyntax;

import file.reader.FileReader;
import file.reader.FileReaderImpl;
import file.writer.FileWriter;
import file.writer.FileWriterImpl;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.converter.DataConverter;
import service.converter.DataConverterImpl;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.OperationStrategy;
import service.operation.OperationStrategyImpl;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import service.report.ReportGenerator;
import service.report.ReportGeneratorImpl;
import service.shop.ShopService;
import service.shop.ShopServiceImpl;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read("reportToRead.csv");
        DataConverter dataConverter = new DataConverterImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.handleTransaction(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, "finalReport.csv");
    }
}
