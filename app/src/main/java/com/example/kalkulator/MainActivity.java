package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[] tombolNumeric = {R.id.nol, R.id.satu, R.id.dua, R.id.tiga, R.id.empat, R.id.lima, R.id.enam, R.id.tujuh, R.id.delapan, R.id.sembilan};
    private int[] tombolOperator = {R.id.kali, R.id.bagi, R.id.tambah, R.id.kurang};

    private TextView hasil;

    private TextView hasil2;

    private boolean angkaTerakhir;

    private boolean kaloError;

    private boolean setelahTitik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.hasil = (TextView) findViewById(R.id.hasil);
        this.hasil2 = (TextView) findViewById(R.id.hasil2);

        setNumericPadaSaatDiKlik();
        setOperatorPadaSaatDiKlik();
    }

    private void setNumericPadaSaatDiKlik() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button tombol = (Button) v;
                if (kaloError) {
                    hasil.setText(tombol.getText());
                    kaloError = false;

                } else {
                    hasil.append(tombol.getText());
                }

                angkaTerakhir = true;
            }
        };

        for (int id : tombolNumeric) {
            findViewById(id).setOnClickListener(listener);
        }

    }

    private void setOperatorPadaSaatDiKlik() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloError) {
                    Button tombol = (Button) v;
                    hasil.append(tombol.getText());
                    angkaTerakhir = false;
                    setelahTitik = false;
                }
            }
        };

        for (int id : tombolOperator) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.koma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloError && !setelahTitik) {
                    hasil.append(".");
                    angkaTerakhir = false;
                    setelahTitik = true;
                }
            }

        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasil.setText("");
                hasil2.setText("");

                angkaTerakhir = false;
                setelahTitik = false;
                kaloError = false;
            }
        });

        findViewById(R.id.sama_dengan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onEqual();

            }

            private void onEqual() {
                if (angkaTerakhir && !kaloError) {
                    String txt = hasil.getText().toString();
                    Expression expression = new ExpressionBuilder(txt).build();
                    try {
                        double result = expression.evaluate();
                        hasil2.setText(Double.toString(result));
                        if (result == (int) result) {
                            // If it's a whole number, display it without decimal places
                            hasil2.setText(Integer.toString((int) result));
                        } else {
                            // If it's not a whole number, display it as it is
                            hasil2.setText(Double.toString(result));
                        }
                        setelahTitik = true;
                    } catch (ArithmeticException ex) {
                        hasil2.setText(("ERROR WOY"));
                        kaloError = true;
                        angkaTerakhir = false;
                    }
                }
            }
        });
    }
}