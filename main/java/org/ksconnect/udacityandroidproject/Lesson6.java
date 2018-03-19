package org.ksconnect.udacityandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class Lesson6 extends AppCompatActivity {

    int quantity = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson6);
        display(quantity);
        displayPrice(quantity * 5);
    }

    public void submitOrder(View view){
        //display(quantity);
        displayPrice(quantity * 5);
    }

    private void display(int count) {
        TextView txtCount = findViewById(R.id.txt_count);
        txtCount.setText("" + count);
    }

    private void displayPrice(int price) {
        TextView priceTextView = findViewById(R.id.txt_price);
        priceTextView.setText("Total: $" + price + "\nThank You!");
    }

    public void incrementQuantity(View view) {
        quantity++;
        display(quantity);
        //displayPrice(quantity * 5);
    }

    public void decrementQuantity(View view) {
        quantity--;
        display(quantity);
        //displayPrice(quantity * 5);
    }
}
