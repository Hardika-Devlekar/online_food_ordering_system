package com.example.sizzlingbites.ui.user.home;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.model.MainCourseModel;
import com.example.sizzlingbites.ui.user.home.model.MainModel;
import com.example.sizzlingbites.ui.user.home.model.OfferModel;
import com.example.sizzlingbites.ui.user.home.model.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    //init layout views
    ImageView detailedImage, detailedremove, detailedadd;
    TextView detailedName;
    TextView detailedDesc;
    TextView detailedRating;
    TextView detailedPrice;
    TextView detailedDiscountPrice;
    TextView detailedNumber;
    Button btnCart;

    int totalQuantity = 1;
    int totalPrice = 0;

    OfferModel offerModel = null;
    MainModel mainModel = null;
    ViewModel viewModel = null;
    MainCourseModel mainCourseModel = null;

    private FirebaseFirestore rootNode;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        setUpToolbar();

        //Hooks
        detailedImage = findViewById(R.id.detailedImage);
        detailedName = findViewById(R.id.detailedName);
        detailedDesc = findViewById(R.id.detailedDesc);
        detailedPrice = findViewById(R.id.detailedPrice);
        detailedDiscountPrice = findViewById(R.id.detailedDiscountPrice);
        detailedRating = findViewById(R.id.detailedRating);
        detailedadd = findViewById(R.id.detailedadd);
        detailedremove = findViewById(R.id.detailedremove);
        detailedNumber = findViewById(R.id.detailedNumber);
        btnCart = findViewById(R.id.btnCart);

        //Initialization
        rootNode = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //strike through on original price of item
        detailedPrice.setPaintFlags(detailedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final Object object = getIntent().getSerializableExtra("detailed");
        if (object instanceof MainModel) {
            mainModel = (MainModel) object;
        } else if (object instanceof MainCourseModel) {
            mainCourseModel = (MainCourseModel) object;
        } else if (object instanceof OfferModel) {
            offerModel = (OfferModel) object;
        } else if (object instanceof ViewModel) {
            viewModel = (ViewModel) object;
        }

        //Adding Items to Cart
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
                submitOrder();
            }
        });

        //Adding elegant items
        detailedadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    detailedNumber.setText(String.valueOf(totalQuantity));

                    if (mainModel != null) {
                        totalPrice = mainModel.getDiscountPrice() * totalQuantity;
                    }
                    if (mainCourseModel != null) {
                        totalPrice = mainCourseModel.getPrice() * totalQuantity;
                    }
                    if (offerModel != null) {
                        totalPrice = offerModel.getPrice() * totalQuantity;
                    }
                    if (viewModel != null) {
                        totalPrice = viewModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        detailedremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 0) {
                    totalQuantity--;
                    detailedNumber.setText(String.valueOf(totalQuantity));
                }
            }
        });

        //Today's Special
        if (mainModel != null) {
            Glide.with(getApplicationContext()).load(mainModel.getImage_url()).into(detailedImage);
            detailedName.setText(mainModel.getName());
            detailedDesc.setText(mainModel.getDescription());
            detailedRating.setText(mainModel.getRating());
            detailedPrice.setText("₹" + mainModel.getPrice());
            detailedDiscountPrice.setText("₹" + mainModel.getDiscountPrice());

            totalPrice = mainModel.getDiscountPrice() * totalQuantity;
        }

        //Main Course
        if (mainCourseModel != null) {
            Glide.with(getApplicationContext()).load(mainCourseModel.getImage_url()).into(detailedImage);
            detailedName.setText(mainCourseModel.getName());
            detailedDesc.setText(mainCourseModel.getDesc());
            detailedRating.setText(mainCourseModel.getRating());
            detailedPrice.setText("₹" + mainCourseModel.getPrice());
            detailedDiscountPrice.setText("₹" + mainCourseModel.getDiscountPrice());

            totalPrice = mainCourseModel.getDiscountPrice() * totalQuantity;
        }


        //Offer Category
        if (offerModel != null) {
            Glide.with(getApplicationContext()).load(offerModel.getImage_url()).into(detailedImage);
            detailedName.setText(offerModel.getName());
            detailedDesc.setText(offerModel.getDesc());
            detailedRating.setText(offerModel.getRating());
            detailedPrice.setText("₹" + offerModel.getPrice());
            detailedDiscountPrice.setText("₹" + offerModel.getDiscountPrice());

            totalPrice = offerModel.getDiscountPrice() * totalQuantity;
        }


        //Row Category
        if (viewModel != null) {
            Glide.with(getApplicationContext()).load(viewModel.getImage_url()).into(detailedImage);
            detailedName.setText(viewModel.getName());
            detailedDesc.setText(viewModel.getDescription());
            detailedRating.setText(viewModel.getRating());
            detailedPrice.setText("₹" + viewModel.getPrice());
            detailedDiscountPrice.setText("₹" + viewModel.getDiscountPrice());

            totalPrice = viewModel.getDiscountPrice() * totalQuantity;
        }
    }

    //adding order
    private void submitOrder() {
        //for order date & order time
        String saveDate, saveTime;
        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(calender.getTime());

        //setup order data
        FirebaseUser mUser = mAuth.getCurrentUser();
        DocumentReference reference = rootNode.collection("Orders").document(mUser.getUid());
        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("orderDate", saveDate);
        orderMap.put("orderTime", saveTime);
        orderMap.put("orderName", detailedName.getText().toString());
        orderMap.put("orderQuantity", detailedNumber.getText().toString());
        orderMap.put("orderStatus", "In Progress");
        reference.set(orderMap);
    }

    //adding items to cart
    private void addedToCart() {
        //for cart date & cart time
        String saveCurrentDate, saveCurrentTime;
        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calender.getTime());

        //setup cart data
        FirebaseUser mUser = mAuth.getCurrentUser();
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("itemName", detailedName.getText().toString());
        cartMap.put("itemPrice", detailedDiscountPrice.getText().toString());
        cartMap.put("saveCurrentDate", saveCurrentDate);
        cartMap.put("saveCurrentTime", saveCurrentTime);
        cartMap.put("itemQuantity", detailedNumber.getText().toString());
        //cartMap.put("discountPrice", detailedDiscountPrice.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        rootNode.collection("Users").document(mUser.getUid())
                .collection("AddToCart").add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Selected item is added to your cart...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
    }
}