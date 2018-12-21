package com.blazesoft.workshopapp.activities;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.astuetz.PagerSlidingTabStrip;
import com.blazesoft.workshopapp.MainActivity;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.NavigationItem;
import com.blazesoft.workshopapp.adapters.PageAdapter;
import com.blazesoft.workshopapp.datastore.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    public static ViewPager viewPager;
    View loginView,approval_layout;
    ListView NavigationItem;
    private  static PageAdapter pageAdapter;
    private  static  String [] PAGES= new String[]{"Pending"};
    private  static  List<String> responsibilities= new ArrayList<String>();
   ;
    private static  List<String> userResponsibility=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responsibilities.clear();
        responsibilities.add("Create Workshop");
        responsibilities.add("Review Workshop");
        responsibilities.add("Approved Woskshop");
        responsibilities.add("Review Consultant");

        responsibilities.add("Log Out");
        String roles= LocalDatabase.getUserRoles(ApprovalActivity.this);
        userResponsibility.clear();
        RoleController roleController=new RoleController();
        userResponsibility.addAll(roleController.getResponsibility(roles));
        final NavigationItem navigationItem=new NavigationItem(ApprovalActivity.this,android.R.layout.simple_list_item_1,userResponsibility.toArray());
        setContentView(R.layout.activity_approval);
        // Give the PagerSlidingTabStrip the ViewPager
        NavigationItem=findViewById(R.id.ListItems);
        NavigationItem.setAdapter(navigationItem);
        viewPager=findViewById(R.id.pager);
       // PagerSlidingTabStrip tabsStrip = findViewById(R.id.tabs);
        pageAdapter=new PageAdapter( viewPager,ApprovalActivity.this,PAGES);
        viewPager.setAdapter(pageAdapter);
       // tabsStrip.setViewPager(viewPager);
        approval_layout=findViewById(R.id.approval_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(ApprovalActivity.this);
        NavigationItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              String roleType=  ((String) NavigationItem.getAdapter().getItem(position));
              if(roleType.equals("Create Workshop")){

                  Intent i=new Intent(ApprovalActivity.this,ActivityMaker.class);
                  startActivity(i);

                }
                if(roleType.equals("Review Workshop")){

                   Intent i =new Intent(ApprovalActivity.this,dashboardsReview.class);
                   startActivity(i);

                }
                if(roleType.equals("Add Admin")){

                    Intent i=new Intent(ApprovalActivity.this,AddAdmin.class);
                    startActivity(i);

                } if(roleType.equals("Add Attendee")){
                    Intent i=new Intent(ApprovalActivity.this,activity_attendee_reg.class);
                    startActivity(i);

                }
                if(roleType.equals("Approved Woskshop")){

                 Intent i =new Intent(ApprovalActivity.this, full_approved_workshop.class);
                 startActivity(i);

                }
                if(roleType.equals("Add Consultant")){

                    Intent i=new Intent(ApprovalActivity.this,AddConsultants.class);
                    startActivity(i);

                }
                if(roleType.equals("Review Consultant")){

                    Intent i=new Intent(ApprovalActivity.this,PendingConsultants.class);
                    startActivity(i);

                }   if(roleType.equals("Workshop Per Status")){

                    Intent i=new Intent(ApprovalActivity.this,SuspendedWorkshop.class);
                    startActivity(i);

                }
                if(roleType.equals("See Charts")){

                    Intent i=new Intent(ApprovalActivity.this,DashBoard.class);
                    startActivity(i);

                }
                if(roleType.equals("Log Out")){
                     LocalDatabase.ClearToken(ApprovalActivity.this);
                     LocalDatabase.ClearUserRoles(ApprovalActivity.this);
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }


            }
        });

    }
    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }
    @Override
    public void onDrawerOpened(@NonNull View view) {

    }
    @Override
    public void onDrawerClosed(@NonNull View view) {

    }
    @Override
    public void onDrawerStateChanged(int i) {

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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       String roles= LocalDatabase.getUserRoles(ApprovalActivity.this);//"admin maker";
        int id = item.getItemId();
        if (id == R.id.CreateWorkshop) {
                if(roles.equals("super admin")||roles.equals("admin maker")){

                    Intent i=new Intent(ApprovalActivity.this,ActivityMaker.class);
                    startActivity(i);

                }else{
                    Toast.makeText(ApprovalActivity.this,"You dont have a privilege to create a workshop",Toast.LENGTH_LONG).show();

                }

            // review workshop role allocation
        } else if (id == R.id.review) {


            if(roles.equals("super admin")||roles.equals("admin checker")){

                viewPager.setCurrentItem(0);
              //  approval_layout.setVisibility(View.VISIBLE);

            }else{
                Toast.makeText(ApprovalActivity.this,"You dont have a privilege to review a workshop",Toast.LENGTH_LONG).show();

            }
//Role to add admin allication
        } else if (id == R.id.AddAdmins) {
            if(roles.equals("super admin")){

                Intent i=new Intent(ApprovalActivity.this,AddAdmin.class);
                startActivity(i);

            }else {
                Toast.makeText(ApprovalActivity.this,"You dont have a privilege to add admin",Toast.LENGTH_LONG).show();

            }
//Role to add attendee you must be a facilitator
        } else if (id == R.id.AddAttendes) {
            if(roles.equals("super admin")||roles.equals("facilitator")){
                viewPager.setCurrentItem(1);
//                Intent intent=new Intent(ApprovalActivity.this,activity_attendee_reg.class);
//                startActivity(intent);

            }else{
                Toast.makeText(ApprovalActivity.this,"You dont have a preveledge to add attendee",Toast.LENGTH_LONG).show();


            }

// allows everybodybody to  see all the rejected workshops
        } else if (id == R.id.rejected) {

            Intent i = new Intent(ApprovalActivity.this, RejectedWoshop.class);

// allow usre to see all the completed workshops
        } else if (id == R.id.CompletedWorkshop) {

// allow users to see all workshop with apending approval status

        }else if (id == R.id.pending) {
            viewPager.setCurrentItem(0);
// this alow all usre to see the approved workshops
        }else if (id == R.id.approved) {
            viewPager.setCurrentItem(1);

        }else if (id == R.id.logout) {
            LocalDatabase.clearToken(ApprovalActivity.this);
            Intent i = getApplicationContext().getPackageManager()
                    .getLaunchIntentForPackage( getApplication().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class RoleController{

        public  List<String> getResponsibility(String userRole){
            ArrayList<String> res=   new ArrayList<String>() ;
            res.clear();
            switch (userRole){
                case "super admin":
                res.addAll(responsibilities);
                    return res;
                case "admin checker":
                    res.add(responsibilities.get(1));
                    res.add(responsibilities.get(5));
                    res.add(responsibilities.get(8));
                    return res;

                case "admin maker":
                    res.add(responsibilities.get(0));
                    res.add(responsibilities.get(5));
                    res.add(responsibilities.get(8));
                    return res;
                case "facilitator":
                    res.add(responsibilities.get(4));
                    res.add(responsibilities.get(5));
                    res.add(responsibilities.get(8));
                    return res;
            }
           return res;
        }



    }
}
