package br.com.casaodocodigo.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, SimpleAdapter.ViewBinder {


    private int viagemSelecionada;


    private AlertDialog criaDialogConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_viagem);
        builder.setPositiveButton(getString(R.string.sim), this);
        builder.setNegativeButton(getString(R.string.nao), this);
        return builder.create();
    }


    private AlertDialog criaAlertDialog() {
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
                getString(R.string.remover)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, this);
        return builder.create();
    }

    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;

    @Override
    public void onClick(DialogInterface dialog, int item) {
// Vamos implementar esse método

        switch (item) {
            case 0:
                startActivity(new Intent(this, ViagemActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, GastoActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GastoListActivity.class));
                break;
            case 3:
                //  viagens.remove(this.viagemSelecionada);
                //getListView().invalidateViews();
                dialogConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                viagens.remove(this.viagemSelecionada);
                getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position,
                            long id) {

        this.viagemSelecionada = position;
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //     setContentView(R.layout.lista_viagem);

       /* setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listarViagens()));
        ListView listView = getListView();
        listView.setOnItemClickListener(this);*/


        /*String[] de = {"imagem", "destino", "data", "total"};
        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor};
        */

        String[] de = {"imagem", "destino", "data","total", "barraProgresso"};
        int[] para = {R.id.tipoViagem, R.id.destino,
                R.id.data, R.id.valor, R.id.barraProgresso};

        SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(), R.layout.lista_viagem, de, para);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        this.alertDialog = criaAlertDialog(); //acionando opcoe ao selecionar o item
        this.dialogConfirmacao = criaDialogConfirmacao();//acionado confirmacao ao excluir
    }


    //ArrayAdapter returna List

    /*
    private List<String> listarViagens() {
        return Arrays.asList("São Paulo", "Bonito", "Maceió");
    }*/


    //SimpleAdapter returna Map
    private List<Map<String, Object>> viagens;

    private List<Map<String, Object>> listarViagens() {
        /*viagens = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.negocios);
        item.put("destino", "São Paulo");
        item.put("data", "02/02/2012 a 04/02/2012");
        item.put("total", "Gasto total R$ 314,98");
        viagens.add(item);
        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Maceió");
        item.put("data", "14/05/2012 a 22/05/2012");
        item.put("total", "Gasto total R$ 25834,67");
        viagens.add(item);

        return viagens;*/
        viagens = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.negocios);
        item.put("destino", "São Paulo");
        item.put("data", "02/02/2012 a 04/02/2012");
        item.put("total", "Gasto total R$ 314,98");
        item.put("barraProgresso", new Double[]{500.0, 450.0, 314.98});
        viagens.add(item);
        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Maceió");
        item.put("data", "14/05/2012 a 22/05/2012");
        item.put("total", "Gasto total R$ 25834,67");
        item.put("barraProgresso", new Double[]{ 30000.0, 28600.0, 25834.67 });
        viagens.add(item);


        return viagens;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viagem_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {

        if (view.getId() == R.id.barraProgresso) {
            Double valores[] = (Double[]) data;
            ProgressBar progressBar = (ProgressBar) view;
            progressBar.setMax(valores[0].intValue());
            progressBar.setSecondaryProgress(valores[1].intValue());
            progressBar.setProgress(valores[2].intValue());
            return true;
        }
        return false;
    }
    //o método de OnItemClickListener invocado pela ListView quando um item é escolhido

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Utilizando List
      //  TextView textView = (TextView) view;
        //String mensagem = "Viagem selecionada: " + textView.getText();
       // Toast.makeText(getApplicationContext(), mensagem,
       //         Toast.LENGTH_SHORT).show();
       // startActivity(new Intent(this, GastoListActivity.class));
        //Utilizando Map
        Map<String, Object> map = viagens.get(position);
        String destino = (String) map.get("destino" );
        String mensagem = "Viagem selecionada: "+ destino;
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GastoListActivity.class));
    }
*/
}
