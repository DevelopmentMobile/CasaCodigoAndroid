package br.com.casaodocodigo.boaviagem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {


    private List<Map<String, Object>> gastos;
    private String dataAnterior = "";

    //ViewBindeer para fazer agrupamento, e vinucalacao dados e widgets
    private class GastoViewBinder implements SimpleAdapter.ViewBinder {


        /*
        * View - recuperada a partir de um id passado no String para[] ;
        * Object - que é o valor armazenado com a chave equivalente ao String de[] .
        * String - uma representação em formato texto do dado passado ( Object data), que será ou o
        * resultado do método toString() ou uma String vazia, sendo garantido que seu valor jamais
        * será nulo.
        */

        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {
            if (view.getId() == R.id.data) {
                if (!dataAnterior.equals(data)) {
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE); //view não é exibida
                } else {
                    view.setVisibility(View.GONE); //view não é exibida mas continua ocupando lugar no layout.
                }
                return true;
            }
            if (view.getId() == R.id.categoria) {
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*          setContentView(R.layout.lista_gasto);

      setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listarGastos()));
        ListView listView = getListView();
        listView.setOnItemClickListener(this);*/

        String[] de = {"data", "descricao", "valor", "categoria"};
        int[] para = {R.id.data, R.id.descricao,
                R.id.valor, R.id.categoria};
        SimpleAdapter adapter = new SimpleAdapter(this,
                listarGastos(), R.layout.lista_gasto, de, para);
        adapter.setViewBinder(new GastoViewBinder());//Depois usar ViewBinder
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //Registrando View que apresetará o Menu de Contexto.
        registerForContextMenu(getListView());


    }


    private List<Map<String, Object>> listarGastos() {
        gastos = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("data", "04/02/2012");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 260,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

/*
        item = new HashMap<String, Object>();
        item.put("data", "03/02/2012");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 260,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("data", "02/02/2012");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 260,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("data", "02/02/2012");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 260,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);
*/
        return gastos;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gasto_list, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_gasto:
                startActivity(new Intent(this, GastoActivity.class));

                return true;
            case R.id.remover:
//remover viagem do banco de dados
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gasto_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remover) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();/*Para recuperar informações sobre o item do menu que foi selecionado, utilizamos o método que retorna um objeto do tipo
            AdapterContextMenuInfo, que fornece o id da linha que foi selecionada, a posi ção do item no adapter e a view que foi selecionada*/
            gastos.remove(info.position);
            getListView().invalidateViews(); // a ListView desenhe as linhas novamente com base no adapter que agora tem um item a menos
            dataAnterior = "";
// remover do banco de dados
            return true;
        }
        return super.onContextItemSelected(item);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        /*Toast.makeText(this, "Gasto selecionado: " + textView.getText(),
                Toast.LENGTH_SHORT).show();*/

        Map<String, Object> map = gastos.get(position);
        String descricao = (String) map.get("descricao");
        String mensagem = "Gasto selecionada: " + descricao;
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }
/*
    private List<String> listarGastos() {
        return Arrays.asList("Sanduíche R$ 19,90",
                "Táxi Aeroporto - Hotel R$ 34,00",
                "Revista R$ 12,00");
    }
*/
}
