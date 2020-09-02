package com.araoz.doktuz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    private ExpandableListView expLV;
    private ExpLVAdapter adapter;
    private ArrayList<String> listCategorias;
    private Map<String, ArrayList<String>> mapChild;


    Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        mtoolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("¿Qué incluye?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        expLV = (ExpandableListView) findViewById(R.id.expl);
        listCategorias = new ArrayList<>();
        mapChild = new HashMap<>();



        cargarDatos();
    }


    private void cargarDatos() {

        ArrayList<String> listEvaluacion = new ArrayList<>();
        ArrayList<String> listChat = new ArrayList<>();
        ArrayList<String> listConsultas = new ArrayList<>();




        listCategorias.add("Evaluacion Completa ¡Gratis!");

        listCategorias.add("Chat Médico ¡Gratis!");

        listCategorias.add("Consultas y exámenes adicionales a precios especiales para Doktuz Card");

        listEvaluacion.add(
                "• Examen Médico Clínico \n" +
                "• Examen Muscoesquelético \n" +
                "• Examen de Audiometría \n" +
                "• Examen de Espirometría (pulmones)\n" +
                "• Examen de Odontológico \n" +
                "• Examen Oftalmológico \n" +
                "• Hermograma Completo \n" +
                "• Grupo Sanguíneo y Factor RH \n" +
                "• Perfil de Lipidos completo (colesterol total,Triglicéridos,HDL,LDL,VLDL) \n" +
                "• Glucosa Ayunas");

        listChat.add("¡Felicidades! \n" +
                "    Ahora eres parte de nuestra comunidad \n" +
                "    disfruta de atención médica cercana y confiable al mejor precio.");

        listConsultas.add("Para disfrutar de todos los benedicios de tu Doktuz Card. \n" +
                          "Agenda tu evaluación completa ¡Ahora!");


        mapChild.put(listCategorias.get(0),listEvaluacion);
        mapChild.put(listCategorias.get(1),listChat);
        mapChild.put(listCategorias.get(2),listConsultas);


        adapter = new ExpLVAdapter(listCategorias, mapChild, this);

        expLV.setAdapter(adapter);


    }


    }

