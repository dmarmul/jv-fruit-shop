package service.converter;

import java.util.List;
import model.FruitTransaction;

public interface DataConverter {
    List<FruitTransaction> convertToTransaction(List<String> inputReport);
}
