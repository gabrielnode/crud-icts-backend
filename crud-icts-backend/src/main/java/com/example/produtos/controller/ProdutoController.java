package com.example.produtos.controller;

import com.example.produtos.exception.ResourceNotFoundException;
import com.example.produtos.model.Produto;
import com.example.produtos.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    public List<Produto> getAllNotes() {
        return produtoRepository.findAll();
    }

    @PostMapping("/produtos")
    public Produto createNote(@Valid @RequestBody Produto produto) {
    	System.out.println(produto);
        return produtoRepository.save(produto);
    }

    @GetMapping("/produtos/{id}")
    public Produto getNoteById(@PathVariable(value = "id") Long noteId) {
        return produtoRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/produtos/{id}")
    public Produto updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody Produto noteDetails) {

        Produto note = produtoRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setNome(noteDetails.getNome());
        note.setPreco(noteDetails.getPreco());

        Produto updatedNote = produtoRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Produto note = produtoRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        produtoRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
