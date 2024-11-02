package com.example.practicahallowen;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.ULocale;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button buttonsusto;
    //int[]idspiner;
    //int[]idedittext;
    //int[]idimagebutton;

    int total=0, bandera=0;
    int estado=0;
    String[]sonidos;
    ArrayList<Integer>idspiner=new ArrayList<Integer>();
    ArrayList<Integer>idedittext=new ArrayList<Integer>();
    ArrayList<Integer>idimagebutton=new ArrayList<Integer>();
    Thread hilosecundario=null;
    Boolean hilo=true;
    TextView textViewnumero;
    LinearLayout linearLayoutlistar;
    MediaPlayer mediaPlayer1,mediaPlayer2,mediaPlayer3,mediaPlayer4,mediaPlayer5,mediaPlayer6,mediaPlayer7,mediaPlayer8,mediaPlayer9,mediaPlayer10;
    Button buttonexit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonsusto=findViewById(R.id.buttonsustos);
        textViewnumero=findViewById(R.id.editTextNumber);
        linearLayoutlistar=findViewById(R.id.Linearlayoutlistar);
        buttonexit=findViewById(R.id.buttonexit);


            sonidos=new String[]{getString(R.string.baño_acido),getString(R.string.sonido_animales),getString(R.string.murcielago),getString(R.string.abejas),getString(R.string.campana),getString(R.string.huesos_rompiendose),getString(R.string.respiracion),getString(R.string.burbujeante),getString(R.string.gato),getString(R.string.puerta_celda)};

            //sonidos=new String[]{"Acid bath","Animal Sounds","Bats","Bees","Bell toll","Bones breaking","Breathing","Bubbling","Cat","Cell door"};


        mediaPlayer1=MediaPlayer.create(this, R.raw.acid_bath);
        mediaPlayer2=MediaPlayer.create(this, R.raw.animal_sounds);
        mediaPlayer3=MediaPlayer.create(this, R.raw.bats);
        mediaPlayer4=MediaPlayer.create(this, R.raw.bees);
        mediaPlayer5=MediaPlayer.create(this, R.raw.bell_toll);
        mediaPlayer6=MediaPlayer.create(this, R.raw.bones_breaking);
        mediaPlayer7=MediaPlayer.create(this, R.raw.breathing);
        mediaPlayer8=MediaPlayer.create(this, R.raw.bubbling);
        mediaPlayer9=MediaPlayer.create(this, R.raw.cat);
        mediaPlayer10=MediaPlayer.create(this, R.raw.cell_door);


        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
                //finish();
            }
        });

        buttonsusto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra=textViewnumero.getText().toString();

                if(palabra.isEmpty())
                {
                    Toast.makeText(MainActivity.this ,"ERROR debes de introducir un numero",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ArrayList<Boolean>comprobar=new ArrayList<Boolean>();
                    ArrayList<Integer>banderacomprobar=new ArrayList<Integer>();
                    int num=Integer.parseInt(palabra);
                    total=num;
                    if(num>=1&&num<11)
                    {

                        for(int i=0;i<num;i++)
                        {
                            Spinner listas=new Spinner(MainActivity.this);
                            ArrayAdapter <String> arrayAdapter;
                            arrayAdapter=new ArrayAdapter<String>(MainActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sonidos);
                            listas.setAdapter(arrayAdapter);
                            listas.setSelection(randomdevolver());
                            LinearLayout linearLayout2 = new LinearLayout(MainActivity.this);
                            EditText textViewsegundos=new EditText(MainActivity.this);
                            textViewsegundos.setInputType(InputType.TYPE_CLASS_NUMBER);
                            textViewsegundos.setText(randomdevolver2());
                            ImageButton buttonplay=new ImageButton(MainActivity.this);
                            buttonplay.setImageResource(R.mipmap.play4);
                            Button buttonpausa=new Button(MainActivity.this);
                            buttonpausa.setText(R.string.otropausa);
                            linearLayout2.addView(listas);
                            linearLayout2.addView(textViewsegundos);
                            linearLayout2.addView(buttonplay);
                            linearLayout2.addView(buttonpausa);
                            int pos=i;

                            comprobar.add(true);
                            banderacomprobar.add(0);

                            listas.setId(View.generateViewId());
                            idspiner.add(listas.getId());
                            textViewsegundos.setId(View.generateViewId());
                            idedittext.add(textViewsegundos.getId());
                            buttonplay.setId(View.generateViewId());
                            idimagebutton.add(buttonplay.getId());


                            linearLayoutlistar.addView(linearLayout2);

                            buttonplay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    listas.setEnabled(false);
                                    textViewsegundos.setEnabled(false);
                                    buttonplay.setEnabled(false);
                                    String palabra=listas.getSelectedItem().toString();
                                    int num=Integer.parseInt(textViewsegundos.getText().toString());
                                    if(num==0)
                                    {
                                        Toast.makeText(MainActivity.this ,"ERROR debes de introducir un numero que no sea 0",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Cuentaatras cuenta1=new Cuentaatras(textViewsegundos,num,buttonplay,listas, palabra, total,pos, comprobar, banderacomprobar, buttonpausa);
                                        cuenta1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    }


                                }
                            });

                            buttonpausa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(banderacomprobar.get(pos)==0)
                                    {
                                        total--;
                                        ((ViewGroup)buttonplay.getParent()).removeView(buttonplay);
                                        ((ViewGroup)textViewsegundos.getParent()).removeView(textViewsegundos);
                                        ((ViewGroup)listas.getParent()).removeView(listas);
                                        ((ViewGroup)buttonpausa.getParent()).removeView(buttonpausa);
                                        if(total==0)
                                        {
                                            buttonsusto.setEnabled(true);
                                            textViewnumero.setEnabled(true);
                                        }
                                    }
                                    else
                                    {
                                        if(estado==0)
                                        {
                                            comprobar.set(pos,false);
                                            estado=1;
                                        }
                                        else
                                        {
                                            Log.d("Comprobar_main","dentro2");
                                            String palabra2=listas.getSelectedItem().toString();
                                            int num2=Integer.parseInt(textViewsegundos.getText().toString());
                                            comprobar.set(pos,true);
                                            Cuentaatras cuenta1=new Cuentaatras(textViewsegundos,num2,buttonplay,listas, palabra2, total,pos, comprobar, banderacomprobar, buttonpausa);
                                            cuenta1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            estado=0;
                                        }
                                    }
                                }
                            });
                        }
                        buttonsusto.setEnabled(false);
                        textViewnumero.setEnabled(false);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this ,"ERROR debes de introducir un numero entre 1 y 10",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    static int randomdevolver()
    {
        Random random = new Random();
        int num=random.nextInt((9-0)+1)+0;
        return num;
    }

    static String randomdevolver2()
    {
        Random random = new Random();
        int num=random.nextInt((60-0)+1)+0;
        String letra=Integer.toString(num);
        return letra;
    }


    private class Cuentaatras extends AsyncTask<String,String,String>
    {
        String palabra;
        int cont, total, pos;
        EditText editText;
        ImageButton button;
        Button button2;
        Spinner spinner;
        ArrayList<Boolean>comprobar=new ArrayList<Boolean>();
        ArrayList<Integer>banderacomprobar=new ArrayList<Integer>();
        public Cuentaatras(EditText editText, int cont,ImageButton button, Spinner spinner, String palabra, int total, int pos, ArrayList<Boolean>comprobar, ArrayList<Integer>banderacomprobar, Button button2)
        {
            this.editText=editText;
            this.cont=cont;
            this.button=button;
            this.spinner=spinner;
            this.palabra=palabra;
            this.total=total;
            this.pos=pos;
            this.comprobar=comprobar;
            this.banderacomprobar=banderacomprobar;
            this.button2=button2;
        }

        @Override
        protected String doInBackground(String... strings)
        {
            banderacomprobar.set(pos,1);
            while(comprobar.get(pos))
            {
                Log.d("Comprobar","dentro");
                String palabra=String.format("%02d",cont);
                publishProgress(palabra);
                cont--;
                if(cont==0&&comprobar.get(pos))
                {
                    comprobar.set(pos,false);
                }
                try
                {

                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            editText.setText(Integer.toString(cont));

        }

        @Override
        protected void onPostExecute(String s) {
            if(cont==0)
            {
                llamar(palabra,editText,cont,button,spinner,total, button2);
            }
        }
    }

    public void llamar(String palabra, EditText editText, int cont, ImageButton button, Spinner spinner, int total, Button button2)
    {
        Reproducir repro1=new Reproducir(palabra,editText,cont,button,spinner,total, button2);
        repro1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class Reproducir extends AsyncTask<String,String,String>
    {
        String cancion;
        EditText editText;
        ImageButton button;
        Spinner spinner;
        int total;
        Button button2;
        public Reproducir(String palabra,EditText editText, int cont,ImageButton button, Spinner spinner, int total, Button button2)
        {
            this.cancion=palabra;
            this.editText=editText;
            this.button=button;
            this.spinner=spinner;
            this.total=total;
            this.button2=button2;
        }
        @Override
        protected String doInBackground(String... strings)
        {
            if(cancion.equals(getString(R.string.baño_acido)))
            {
                mediaPlayer1.start();
                while (mediaPlayer1.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.sonido_animales)))
            {
                mediaPlayer2.start();
                while (mediaPlayer2.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.murcielago)))
            {
                mediaPlayer3.start();
                while (mediaPlayer3.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.abejas)))
            {
                mediaPlayer4.start();
                while (mediaPlayer4.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.campana)))
            {
                mediaPlayer5.start();
                while (mediaPlayer5.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.huesos_rompiendose)))
            {
                mediaPlayer6.start();
                while (mediaPlayer6.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.respiracion)))
            {
                mediaPlayer7.start();
                while (mediaPlayer7.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.burbujeante)))
            {
                mediaPlayer8.start();
                while (mediaPlayer8.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else if(cancion.equals(getString(R.string.gato)))
            {
                mediaPlayer9.start();
                while (mediaPlayer9.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            else
            {
                mediaPlayer10.start();
                while (mediaPlayer10.isPlaying())
                {
                    Log.d("Estado","sonando");
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            ((ViewGroup)button.getParent()).removeView(button);
            ((ViewGroup)editText.getParent()).removeView(editText);
            ((ViewGroup)spinner.getParent()).removeView(spinner);
            ((ViewGroup)button2.getParent()).removeView(button2);
            total--;
            if(total==0)
            {
                buttonsusto.setEnabled(true);
                textViewnumero.setEnabled(true);
            }
        }
    }

}