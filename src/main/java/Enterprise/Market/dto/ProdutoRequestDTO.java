package Enterprise.Market.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
}
