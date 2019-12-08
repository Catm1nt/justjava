/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
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

        /**
         * Define CheckBox whippedCreamCheckBox, then assign it to be a boolean
         */
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.toppingsCream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        /**
         * Define CheckBox chocolateCheckBox, then assign it to be a boolean
         */
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.toppingsChoc);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        /**
        * Defines EditText nameTextField, gets input text from the UI, converts it to String
        */
        EditText nameTextField = (EditText) findViewById(R.id.nameTextField);
        String name = nameTextField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "New coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *@param addWhippedCream is whether or not the user wants whipped cream topping. Needs to be boolean
     *@param addChocolate  is whether or not the user wants chocolate topping. Needs to be boolean
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream){
            basePrice = basePrice +1;
        }

        if (addChocolate){
            basePrice = basePrice +2;
        }
        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * This method creates the order summary with quantity, price and additional messages
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream on their order
     *                        (boolean true or false)
     * @param addChocolate    is whether or not the user wants chocolate on their order (boolean
     *                        true or false)
     * @param nameTextField   inputs the text the user has typed in the EditText view
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameTextField) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n " + getString(R.string.add_chocolate) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + addChocolate;
        priceMessage += "\n " + getString(R.string.quantity_of_coffees) + quantity;
        priceMessage += "\n " + getString(R.string.total_price) + "$" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the + button is clicked.
     */

    public void increment(View view) {
        if (quantity == 100){
            //show an error message as a toast. "this" is context.
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
    }

    /**
     * This method is called when the - button is clicked.
     */

    public void decrement(View view) {
        if (quantity == 0){
            //Show an error message as a toast
            Toast.makeText(this, "You cannot order less than no coffees", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
    }
 }