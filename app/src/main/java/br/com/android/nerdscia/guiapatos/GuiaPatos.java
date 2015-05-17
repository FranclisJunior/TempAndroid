package br.com.android.nerdscia.guiapatos;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import br.com.android.nerdscia.guiapatos.view.adapters.AdapterMain;
import br.com.android.nerdscia.guiapatos.view.views.FragmentCategories;
import br.com.android.nerdscia.guiapatos.view.views.FragmentPlans;

public class GuiaPatos extends ActionBarActivity {
    private String [] CATEGORIES;
    private int [] ICONS;
    private int lastMenu;
    private boolean fistAcess;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_patos);

        CATEGORIES = getResources().getStringArray(R.array.options_menu_array);
        ICONS = new int[]{R.drawable.ic_bus, R.drawable.ic_bus, R.drawable.ic_bus, R.drawable.ic_bus};

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.menu_drawer);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AdapterMain(CATEGORIES,ICONS);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        setEvents();
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        optionSelected(1);
    }

    private void setEvents(){
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                if(!fistAcess)
                    mRecyclerView.getChildAt(1).setSelected(true);
                fistAcess=true;
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        final GestureDetector mGestureDetector = new GestureDetector(GuiaPatos.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    drawer.closeDrawers();
                    optionSelected(recyclerView.getChildPosition(child));
                    return true;
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) { }
        });
    }

    private void openFragment(final Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    public void optionSelected(final int position) {
        if (lastMenu == position || position==0) return;

        changerBackground(position);
        switch (position) {
            case 1:
                openFragment(new FragmentCategories());
                break;
            case 2:
                openFragment(new FragmentPlans());
                break;
            default:
                return;
        }
    }

    private void changerBackground(int position){
        for(int i = 0;i<mRecyclerView.getChildCount();i++)
            mRecyclerView.getChildAt(i).setSelected(i==position);
        lastMenu=position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guia_patos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}