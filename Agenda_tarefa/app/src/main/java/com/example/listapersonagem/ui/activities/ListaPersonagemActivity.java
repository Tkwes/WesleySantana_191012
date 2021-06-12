package com.example.listapersonagem.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {


    //DEFININDO VARIAVEIS
    public static final String TITLE_APPBAR = "Agenda_Tarefa";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITLE_APPBAR);
        //SETENDO POSIÇAO DO LAYOUT

        botaoFAB();
        //Chamando metodo de Botao

        configuraLista();
        //Chamando metodo da lista
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
        //volta a liberar e atualiza lista
    }

    private void atualizaPersonagem() {
        adapter.clear();
        //Limpando a lista

        adapter.addAll(dao.todos());
        //passa novos atributos a lista
    }

    private void remove(Personagem personagem) {

        dao.remove(personagem);
        adapter.remove(personagem);
        //zera a lista novamente, e remove o personagem
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu_, View c, ContextMenu.ContextMenuInfo MenuIntel) {
        //Ao selecionar um iten segurando remove da lista
        super.onCreateContextMenu(menu_, c, MenuIntel);
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu_);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem itens) {

        //exibe menu  e inspeciona novos itens
        configuraMenu(itens);
        return super.onContextItemSelected(itens);
    }

    private void configuraMenu(@NonNull MenuItem item) {

        //passa para a variavel itemID novo item set
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {

            //Cria chat informando novo que vai remover iten
            new AlertDialog.Builder(this)
                    .setTitle("esta açao excluira o item")
                    .setMessage("Tem certeza que deseja excluir?")
                   //condicional se sim
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            //Exclui iten excluido
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Nao", null)
                    .show();
        }
    }

    private void configuraLista() {
        //Busca na lista da activity
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);

        //adicionando a lista o personagem
        listaDePersonagem(listaDePersonagens);


        configuraItemPerClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    //Condicional se clicar na lista
    private void configuraItemPerClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Sobrepondo metodo para selecionar na lista
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Adicionando posicao na lista
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(position);
                //Criando um fomulario no editar
                abreFormularioModoEditar(personagemEscolhido);
            }
        });
    }


    private void abreFormularioModoEditar(Personagem personagem) {
        //adiciona conteudo a variavel
        Intent vaiParaFormulario = new Intent(this, FormularioPersonagemActivity.class);

        //busca informacao do putexta
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);

        //busca do activity e coloca no fomulario
        startActivity(vaiParaFormulario);
    }

    private void listaDePersonagem(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }

    //Criando metodo do botao
    private void botaoFAB() {
        //PASSANDO A VARIAVEL A INFORMACAO DO fab_novo_personagem
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);

        //condicional se clicar
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chama metodo para salvar no fomulario
                abreFormularioSalvar();
            }
        });
    }

    private void abreFormularioSalvar() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }
}


