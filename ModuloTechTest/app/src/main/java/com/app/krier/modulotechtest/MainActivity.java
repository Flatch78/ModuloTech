package com.app.krier.modulotechtest;

import android.Manifest;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.app.krier.modulotechtest.fragment.AbstractFragment;
import com.app.krier.modulotechtest.fragment.contact.ContactsFragment;
import com.app.krier.modulotechtest.fragment.contact.ContactsFragmentListener;
import com.app.krier.modulotechtest.fragment.credit.CreditFragment;
import com.app.krier.modulotechtest.fragment.credit.CreditFragmentListener;
import com.app.krier.modulotechtest.fragment.profile.ProfileFragment;
import com.app.krier.modulotechtest.fragment.profile.ProfileFragmentListener;
import com.app.krier.modulotechtest.fragment.search.SearchFragment;
import com.app.krier.modulotechtest.fragment.search.SearchFragmentListener;
import com.app.krier.modulotechtest.manager.json.JsonRequest;
import com.app.krier.modulotechtest.manager.request.Request;
import com.app.krier.modulotechtest.manager.request.RequestListener;
import com.app.krier.modulotechtest.models.RootModel;

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by GuillaumeK on 15/03/2017.
 */

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ContactsFragmentListener,
        ProfileFragmentListener,
        SearchFragmentListener,
        CreditFragmentListener,
        RequestListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Map<Integer, AbstractFragment> mMapFragment;
    private int mIdTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateMapFragment();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        selectFragment(R.id.nav_contacts_fragment);

        request();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_disconnect) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        selectFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectFragment(int idFragment) {
        AbstractFragment fragment = mMapFragment.get(idFragment);
        if (fragment != null) {
            setTitle(fragment.getIdTitle());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private void generateMapFragment() {
        mMapFragment = new HashMap<>();
        mMapFragment.put(R.id.nav_contacts_fragment, ContactsFragment.newInstance());
        mMapFragment.put(R.id.nav_profile_fragment, ProfileFragment.newInstance());
        mMapFragment.put(R.id.nav_search_fragment, SearchFragment.newInstance());
        mMapFragment.put(R.id.nav_credit_fragment, CreditFragment.newInstance());
    }

    /* Profile Listener */
    @Override
    public void onRequestImage(String url, ImageView imageView) {
        Request.instanceRequest(this).makeImageRequest(url, imageView);
    }

    /* REQUEST */

    void request() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        if (permissionCheck == PERMISSION_GRANTED) {
            Request.instanceRequest(this).makeGetRequest();
        }
    }

    @Override
    public void onResponse(String response) {
        RootModel rootModel = new JsonRequest().makeObjectRootModel(response);
        for (Map.Entry<Integer, AbstractFragment> fragmentEntry : mMapFragment.entrySet()) {
            fragmentEntry.getValue().responseRequest(rootModel);
        }
    }

    @Override
    public void onError(String error) {
        // TODO check error
        Log.e(TAG, "Request error: " + error);
    }

    /* SETTER / GETTER */

    @Override
    public void setTitle(int title) {
        mIdTitle = title;
        mToolbar.setTitle(mIdTitle);
    }


}
