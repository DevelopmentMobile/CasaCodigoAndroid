package br.com.casaodocodigo.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

    }


    public void selecionarOpcao(View view) {
        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        //Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();


        switch (view.getId()) {
            case R.id.nova_viagem:
                startActivity(new Intent(DashboardActivity.this, ViagemActivity.class));
                break;

            case R.id.configuracoes:
                startActivity(new Intent(this, ConfiguracoesActivity.class));
                break;

        }



    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.dashboard_menu, menu);
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



}
