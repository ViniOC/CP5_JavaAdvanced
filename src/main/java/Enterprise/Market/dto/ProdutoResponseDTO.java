package Enterprise.Market.dto;

import Enterprise.Market.entity.Produto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    public ProdutoResponseDTO(Produto entidade) {
        this.id = entidade.getId();
        this.nome = entidade.getNome();
        this.descricao = entidade.getDescricao();
        this.preco = entidade.getPreco();
        this.quantidadeEstoque = entidade.getQuantidadeEstoque();
    }
}
