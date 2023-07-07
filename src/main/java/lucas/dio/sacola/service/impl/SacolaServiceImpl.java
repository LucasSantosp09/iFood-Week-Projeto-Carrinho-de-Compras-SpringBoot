package lucas.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import lucas.dio.sacola.enumeration.FormaPagamento;
import lucas.dio.sacola.model.Item;
import lucas.dio.sacola.model.Restaurante;
import lucas.dio.sacola.model.Sacola;
import lucas.dio.sacola.repository.ItemRepository;
import lucas.dio.sacola.repository.ProdutoRepository;
import lucas.dio.sacola.repository.SacolaRepository;
import lucas.dio.sacola.resource.dto.ItemDto;
import lucas.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {


    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;
    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getSacolaId());

       if (sacola.isFechada()){
           throw new RuntimeException("Esta sacola já fechada");
       }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Essa produto não existe");
                        }
                ))
                .build();


        List<Item> itensDaSacola = sacola.getItens();

        if (itensDaSacola.isEmpty()){
            itensDaSacola.add(itemParaSerInserido);
        }else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)){
                itensDaSacola.add(itemParaSerInserido);
            }else {
                throw new RuntimeException("Não é possivel produtos de restaurantes diferentes. Feche a sacola ou esvazie");
            }


        }

        sacolaRepository.save(sacola);


        return itemRepository.save(itemParaSerInserido);
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
            throw new RuntimeException("Inclua itens na escola");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
