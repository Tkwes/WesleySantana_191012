package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {

    //definindo variaveis necessarias, e incorporando conteudo
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    //cria metodo de salvar
    public void salva(Personagem personagemSalvo) {
        //DEFINE UMA FORMA DE INDENTIFICAR CADA PERSONAGEM
        personagemSalvo.setId((contadorDeId));
        personagens.add(personagemSalvo);
        contadorDeId++;
    }

    //cria metodo de EDISAO DE PERSONAGEM
    public void edita(Personagem personagem) {
        Personagem personagemEscolhido = null;
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                personagemEscolhido = p;
            }
        }
        //CONDICIONAL DE TESTE
        if (personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    //BUSCA PERSONAGEM
    private Personagem buscaPersonagemID(Personagem personagem) {
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }
    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemID(personagem);
        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
