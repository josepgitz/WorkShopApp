package com.blazesoft.workshopapp.newtork.url;

import android.util.Log;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CustomUrlBuilder {
    @Expose  private String url;
    @Expose List<Query> queryList= new ArrayList<>();
    StringBuilder stringBuilder;
    public CustomUrlBuilder(String url){
        this.url= url;

    }
    public String  build(){
        stringBuilder= new StringBuilder(this.url);
        if (queryList.size()>0){

            stringBuilder.append("?");
            for (int i = 0; i < queryList.size(); i++) {
                stringBuilder.append(queryList.get(i).getQuery());
                if (queryList.size()-1==i) break;
                else{
                    stringBuilder.append("&");
                }
            }
        }else{
            Log.e("Query","No news query filter");
        }


return stringBuilder.toString();
    }

    public synchronized CustomUrlBuilder addQuery(Query query){
        queryList.add(query);
        return this;
    }
    public synchronized CustomUrlBuilder appendQuery(Query query){
      if (query!=null){
          for (int i = 0; i < queryList.size(); i++) {
              if (queryList.get(i).getKey().equalsIgnoreCase(query.getKey())){
                  queryList.get(i).setValue(query.getValue());


              }else{
                  if (queryList.size()-1==i){
                      addQuery(query);
                  }
              }

          }


      }
        Log.e("Build",queryList.size()+"");
      if (queryList.size()<1){
          addQuery(query);
      }
        return this;
    }
    public String getUrl() {

        Log.e("Query built",url);
        return build();
    }
}
