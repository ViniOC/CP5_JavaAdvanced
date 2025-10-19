package Enterprise.Market.service;

import Enterprise.Market.dto.ProdutoRequestDTO;
import Enterprise.Market.dto.ProdutoResponseDTO;
import Enterprise.Market.entity.Produto;
import Enterprise.Market.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos(){
        List<Produto>  produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id){

        Produto produto = produtoRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o id: "+ id));

        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Produto produtoSalvo = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o id: " + id));

        produtoExistente.setNome(dto.getNome());
        produtoExistente.setDescricao(dto.getDescricao());
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);

        return new ProdutoResponseDTO(produtoAtualizado);
    }

    @Transactional
    public void deletarProduto(Long id) {

        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com o id: " + id);
        }

        produtoRepository.deleteById(id);
    }
}
