package Enterprise.Market.controller;


import Enterprise.Market.dto.ProdutoRequestDTO;
import Enterprise.Market.dto.ProdutoResponseDTO;
import Enterprise.Market.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listarProdutos(Model model) {

        List<ProdutoResponseDTO> produtos = produtoService.listarTodos();

        model.addAttribute("listaDeProdutos", produtos);

        return "lista-produtos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioDeNovoProduto(Model model) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        model.addAttribute("produtoDTO", dto);
        return "form-produto";
    }
    @PostMapping("/novo")
    public String salvarNovoProduto(@ModelAttribute("produtoDTO") ProdutoRequestDTO dto) {
        produtoService.salvarProduto(dto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        ProdutoResponseDTO dto = produtoService.buscarPorId(id);
        model.addAttribute("produtoDTO", dto);
        return "form-produto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                   @ModelAttribute("produtoDTO") ProdutoRequestDTO dto) {
        produtoService.atualizarProduto(id, dto);

        return "redirect:/produtos";
    }
    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return "redirect:/produtos";
    }
}
