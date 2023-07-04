package lucas.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import lucas.dio.sacola.enumeration.FormaPagamento;
import lucas.dio.sacola.model.Item;
import lucas.dio.sacola.model.Sacola;
import lucas.dio.sacola.repository.SacolaRepository;
import lucas.dio.sacola.resource.dto.ItemDto;
import lucas.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {


    private final SacolaRepository sacolaRepository;
    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        return null;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                ()  -> {
                    throw new RuntimeException("Essa sacola não existe");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {

        Sacola sacola = verSacola(id);

        if(sacola.getItens().isEmpty()){
            throw new RuntimeException("Incua itens na escola");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;


        return null;
    }
}
