package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    //Criando variaveis static
    private static final String TITLE_APPBAR_EDITA_PERSONAGEM = "Editar Contato";
    private static final String TITLE_APPBAR_NOVO_PERSONAGEM = "Novo Contato";

    //criando as variaveis de texto
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoEndereco;
    private EditText campoCep;
    private EditText campoRg;
    private EditText campoGenero;

    private final PersonagemDAO dao = new PersonagemDAO(); //criando um objeto para receber dados
    private Personagem personagem; // Criando objeto personagem

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //CONDICIONAL SE CLICAR
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //condiconal de teste de clique

        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {

            //salvando item
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);

        //Inicializando vampos para digitaçao
        inicializacaoCampos();

        //define botao com um metodo
        configuraBotao();

        //carrega o metodo com personagem
        carregaPersonagem();
    }

    private void carregaPersonagem() {

        //chamando metodo com as informacoes
        Intent dados = getIntent();

        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            //Redefinindo novos titulos
            setTitle(TITLE_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            //auto preeenchimento de campo com informacoes antes salvas
            preencheCampos();
        } else {
            setTitle(TITLE_APPBAR_NOVO_PERSONAGEM);
            //cria um personagem caso nao exista
            personagem = new Personagem();
        }
    }

    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoTelefone.setText(personagem.getTelefone());
        campoEndereco.setText(personagem.getEndereco());
        campoRg.setText(personagem.getRg());
        campoCep.setText(personagem.getCep());
        campoGenero.setText(personagem.getGenero());
    }

    private void configuraBotao() {
        Button botaoSalvar = findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        preenchePersonagem();
        if (personagem.IdValido()) {
            dao.edita(personagem);
        } else {
            dao.salva(personagem);
        }
        //Finalizando o Activity
        finish();
    }

    private void inicializacaoCampos() {

        //buscando regerencias escritas no texto
        campoNome = findViewById(R.id.editText_nome);
        campoAltura = findViewById(R.id.editText_altura);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoEndereco = findViewById(R.id.editText_endereco);
        campoRg = findViewById(R.id.editText_rg);
        campoCep = findViewById(R.id.editText_cep);
        campoGenero = findViewById(R.id.editText_genero);
        //ocutando itens
        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("xxxxx-xxx");
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, smfCep);
        campoCep.addTextChangedListener(mtwCep);
        SimpleMaskFormatter smfRG = new SimpleMaskFormatter("xx.xxx.xxx-x");
        MaskTextWatcher mtwRG = new MaskTextWatcher(campoRg, smfRG);
        campoRg.addTextChangedListener(mtwRG);
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(xx)xxxxx-xxxx");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("x,xx");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("xx/xx/xxxx");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    private void preenchePersonagem() {
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String rg = campoRg.getText().toString();
        String cep = campoCep.getText().toString();
        String genero = campoGenero.getText().toString();
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setRg(rg);
        personagem.setCep(cep);
        personagem.setGenero(genero);
    }
}