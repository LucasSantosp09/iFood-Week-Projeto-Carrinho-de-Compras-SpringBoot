package lucas.dio.sacola.service;

import lucas.dio.sacola.model.Item;
import lucas.dio.sacola.model.Sacola;
import lucas.dio.sacola.resource.dto.ItemDto;

public interface SacolaService {

    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);

}
