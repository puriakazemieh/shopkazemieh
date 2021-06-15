package com.example.mvvm.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SearchEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.auth.AuthenticationActivity;
import com.example.mvvm.auth.OnuserAuthnticate;
import com.example.mvvm.auth.UserInfoManager;
import com.example.mvvm.home.adapterhome.AdapterBanner;
import com.example.mvvm.home.adapterhome.AdapterCategory;
import com.example.mvvm.home.adapterhome.AdapterProduct;
import com.example.mvvm.listcart.ListCartActivity;
import com.example.mvvm.listproduct.ShowListActivity;
import com.example.mvvm.model.MvvmSingleObserve;
import com.example.mvvm.model.TokenContainer;
import com.example.mvvm.order.OrderActivity;
import com.example.mvvm.search.SearchActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity implements AdapterBanner.onClickUrl {
    private RecyclerView rcBanner, rcCategory, rcLatestProduct, rcPopularProduct;
    private TextView txtShowProductLates, txtShowProductPopular;
    private ImageView imgMenu, imgListCart;
    private HomeViewModel homeViewModel;
    private AdapterProduct adapterProductLatest;
    private AdapterProduct adapterProductPopulare;
    private AdapterBanner adapteBanner;
    private AdapterCategory adapterCategory;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Drawer drawer;
    private UserInfoManager userInfoManager;
    private SecondaryDrawerItem authDrawerUser, about;
    private ProfileDrawerItem profileDrawerItem;
    private PrimaryDrawerItem basketItem, orderHistory;
    private AccountHeader accountHeader;
    private Typeface typeface;
    private TextView txtcountProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeViewModel = new HomeViewModel();
        setUpView();
        setDataView();
        observeData();
        setUpDrawer();
    }

    private void setUpView() {
        txtShowProductLates = findViewById(R.id.txt_show_lattest_product);
        txtShowProductPopular = findViewById(R.id.txt_show_lattest_product);
        txtcountProduct = findViewById(R.id.txt_badge_product);
        rcBanner = findViewById(R.id.rc_banner_home);
        rcCategory = findViewById(R.id.rc_category_home);
        rcLatestProduct = findViewById(R.id.rc_product_latest_home);
        rcPopularProduct = findViewById(R.id.rc_product_popular_home);
        imgMenu = findViewById(R.id.img_menu_home);
        imgListCart = findViewById(R.id.img_intent_cartlist);
        userInfoManager = new UserInfoManager(this);
        typeface = ResourcesCompat.getFont(this, R.font.iranyekenreg);
    }

    private void setUpDrawer() {
        setProfileDrawerItem();
        accountHeader = new AccountHeaderBuilder()
                .addProfiles(profileDrawerItem)
                .withActivity(this)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(@Nullable View view, @NonNull IProfile<?> iProfile, boolean b) {
                        if (TokenContainer.getToken() == null) {
                            Intent intent1 = new Intent(HomeActivity.this, AuthenticationActivity.class);
                            startActivity(intent1);
                        }
                        return false;
                    }
                })
                .withTypeface(typeface)
                .withHeaderBackground(ContextCompat.getDrawable(this, R.color.template))
                .withTextColor(ContextCompat.getColor(this, R.color.white))
                .build();
        basketItem = new PrimaryDrawerItem().withName("سبد خرید")
                .withIdentifier(1)
                .withTypeface(typeface)
                .withTextColor(ContextCompat.getColor(this, R.color.template))
                .withBadge("0")
                .withBadgeStyle(new BadgeStyle()
                        .withBadgeBackground(ContextCompat.getDrawable(this, R.drawable.shape_backround_badge))
                        .withTextColor(ContextCompat.getColor(this, R.color.white)))
                // .withIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_shopping_cart_24))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(@Nullable View view, int i, @NonNull IDrawerItem<?> iDrawerItem) {
                        Intent intent1;
                        if (TokenContainer.getToken() != null) {
                            intent1 = new Intent(HomeActivity.this, ListCartActivity.class);
                        } else {
                            intent1 = new Intent(HomeActivity.this, AuthenticationActivity.class);
                        }
                        startActivity(intent1);
                        return false;
                    }
                })
                .withSelectable(false);

        orderHistory = new PrimaryDrawerItem().withName("سفارشات من")
                .withIdentifier(2)
                .withTypeface(typeface)
                .withTextColor(ContextCompat.getColor(this, R.color.gray_900))
                .withIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_shopping_cart_24))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(@Nullable View view, int i, @NonNull IDrawerItem<?> iDrawerItem) {

                        startActivity(new Intent(HomeActivity.this, OrderActivity.class));
                        return false;
                    }
                })
                .withSelectable(false);


        about = new SecondaryDrawerItem().withName("درباره ما")
                .withIdentifier(3)
                .withTypeface(typeface)
                .withIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_shopping_cart_24))
                .withSelectable(false);
        setAuthDrawerUser();
        createDrawer();
    }

    private void setAuthDrawerUser() {

        if (drawer == null) {
            authDrawerUser = new SecondaryDrawerItem().withName("خروج از حساب کاربری")
                    .withIdentifier(4)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(@Nullable View view, int i, @NonNull IDrawerItem<?> iDrawerItem) {
                            if (TokenContainer.getToken() != null) {
                                userInfoManager.clear();
                                TokenContainer.updateToken(null);
                                setAuthDrawerUser();
                                setProfileDrawerItem();
                                createDrawer();

                                CountContainer.updateCount(0);
                                EventBus.getDefault().post(new OnCartItemChangeCount(0));
                                updateItemCount(CountContainer.getCount());


                            } else {
                                Intent intent1 = new Intent(HomeActivity.this, AuthenticationActivity.class);
                                startActivity(intent1);
                            }
                            return false;
                        }
                    })
                    .withTypeface(typeface)
                    .withIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_shopping_cart_24))
                    .withSelectable(false);
        }


    }

    private void setProfileDrawerItem() {
        String name = TokenContainer.getToken() != null ? "" : "کاربر مهمان";
        String email = TokenContainer.getToken() != null ? userInfoManager.getEmail() : "ورود به حساب کاربری یا ثبت نام";
        if (drawer == null) {
            profileDrawerItem = new ProfileDrawerItem()
                    .withName(name)
                    .withTypeface(typeface)
                    .withEmail(email)
                    .withIcon(ContextCompat.getDrawable(this, R.drawable.useraccount));
        } else {
            profileDrawerItem.withName(name)
                    .withEmail(email);
            accountHeader.updateProfile(profileDrawerItem);
            //  Log.e(TAG, "onItemClick: hhhhhhhhhhhhh" +name +email+  profileDrawerItem.withName(name).toString());
        }


    }

    private void setDataView() {
        // setDataLatest
        rcLatestProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterProductLatest = new AdapterProduct();
        rcLatestProduct.setAdapter(adapterProductLatest);
        // setDataPopulare
        rcPopularProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterProductPopulare = new AdapterProduct();
        rcPopularProduct.setAdapter(adapterProductPopulare);
        // setDataBanner
        rcBanner.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapteBanner = new AdapterBanner(this::onClickValue);
        SnapHelper snapHelper = new PagerSnapHelper();
        rcBanner.setAdapter(adapteBanner);
        snapHelper.attachToRecyclerView(rcBanner);
        // setDataCategory
        rcCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterCategory = new AdapterCategory();
        rcCategory.setAdapter(adapterCategory);

    }

    private void observeData() {
        homeViewModel.latestProduct().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Products>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Products> products) {
                        adapterProductLatest.setProductsList(products);
                    }

                });
        homeViewModel.populareProducts().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Products>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Products> products) {
                        adapterProductPopulare.setProductsList(products);
                    }
                });
        homeViewModel.banners().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Banners>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<Banners> banners) {
                        adapteBanner.setBannersList(banners);
                    }
                });
        homeViewModel.categores().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Categories>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<Categories> categories) {
                        adapterCategory.setCategoriesList(categories);
                    }
                });

        getCount();
    }

    private void getCount() {
        homeViewModel.getCountProduct().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<CountProduct>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull CountProduct countProduct) {

                        CountContainer.updateCount(countProduct.getCount());
                        EventBus.getDefault().post(new OnCartItemChangeCount(countProduct.getCount()));
                        updateItemCount(CountContainer.getCount());
                        //  Toast.makeText(HomeActivity.this, String.valueOf(countProduct.getCount()), Toast.LENGTH_SHORT).show();
                        //  txtcountProduct.setText(countProduct.getCount()+"");
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCartItemChange(OnCartItemChangeCount onCartItemChangeCount) {
        updateItemCount(onCartItemChangeCount.getCount());
        Log.e(TAG, "OnuserAuthnticate: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnuserAuthnticate(OnuserAuthnticate onuserAuthnticate) {
        getCount();
        Log.e(TAG, "OnuserAuthnticate: ");
    }


    private void updateItemCount(int count) {
        if (count > 0) {
            txtcountProduct.setVisibility(View.VISIBLE);
            txtcountProduct.setText(String.valueOf(count));
        } else {
            txtcountProduct.setVisibility(View.GONE);
        }
        basketItem.withBadge(String.valueOf(count));
        drawer.updateItem(basketItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void onClick(View view) {
        Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
        switch (view.getId()) {
            case R.id.img_search: {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                break;
            }
            case R.id.txt_show_lattest_product: {
                intent.putExtra(ShowListActivity.EXTRA_KEY_SORT, Products.SORT_LATEST);
                startActivity(intent);
                break;
            }
            case R.id.txt_show_popular_product: {
                intent.putExtra(ShowListActivity.EXTRA_KEY_SORT, Products.SORT_POPULAR);
                startActivity(intent);
                break;
            }
            case R.id.img_menu_home: {
                drawer.openDrawer();
                break;
            }
            case R.id.img_intent_cartlist: {
                Intent intent1;
                if (TokenContainer.getToken() != null) {
                    intent1 = new Intent(HomeActivity.this, ListCartActivity.class);
                } else {
                    intent1 = new Intent(HomeActivity.this, AuthenticationActivity.class);
                }
                startActivity(intent1);
                break;
            }
        }
    }

    @Override
    public void onClickValue(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void createDrawer() {
        if (TokenContainer.getToken() != null) {
            drawer = new DrawerBuilder().withActivity(this)
                    .withSelectedItem(-1)
                    .addDrawerItems(basketItem)
                    .addDrawerItems(orderHistory)
                    .addDrawerItems(about)
                    .addDrawerItems(authDrawerUser)
                    .withAccountHeader(accountHeader)
                    .withDrawerGravity(Gravity.RIGHT).build();
        } else {
            drawer = new DrawerBuilder().withActivity(this)
                    .withSelectedItem(-1)
                    .addDrawerItems(about)
                    .withAccountHeader(accountHeader)
                    .withDrawerGravity(Gravity.RIGHT).build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAuthDrawerUser();
        setProfileDrawerItem();
        updateItemCount(CountContainer.getCount());
        createDrawer();
        getCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAuthDrawerUser();
        setProfileDrawerItem();
        updateItemCount(CountContainer.getCount());
        createDrawer();
        getCount();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else {

            super.onBackPressed();
        }
    }
}