package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //find the user's name
        EditText nameField = (EditText) findViewById(R.id.nameField);
        String name = nameField.getText().toString();
        //figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_chk);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_chk);
        boolean chocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,chocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, chocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    /** method for plus button **/
    public void increment(View view){
        if(quantity == 100){
            //show an error message as Toast
            Toast.makeText(this, "you cannot have more than 100 coffees",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    /** method for (-)button**/
    public void decrement(View view) {
        if (quantity == 1){
            //show an error message as Toast
            Toast.makeText(this, "you cannot have less than 1 coffee",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * @param addWhippedCream
     * @param addChocolate
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream) {
            basePrice = basePrice +1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;

    }

    /**
     * @param name
     * @param price
     * @param hasWhippedCream
     * @param chocolate
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean chocolate){
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream?" + hasWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate?" + chocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal :$" + price;
        priceMessage = priceMessage + "\n Thank you!";
        return priceMessage;
    }
}
