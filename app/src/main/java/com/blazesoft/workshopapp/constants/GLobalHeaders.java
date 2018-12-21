package com.blazesoft.workshopapp.constants;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.blazesoft.workshopapp.datastore.LocalDatabase;

import org.json.JSONException;
import org.json.JSONObject;


public class GLobalHeaders {



    public  static  JSONObject  getGlobalHeaders(AppCompatActivity appCompatActivity){
        JSONObject headers = new  JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization","Bearer " + LocalDatabase.getToken(appCompatActivity));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }
    public  static  JSONObject  getGlobalHeaders(Context context){
        JSONObject headers = new  JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization","Bearer " +"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjkwNTA0ZTI3ZWJlYjQ2NDNkZGM5MmRhN2I1NjRjY2FkMzA1M2EyMzVkNzU0NjA1ODMwZjMwZTkxNGY5ZGYyODk3Njc4ZGI3YjNjMjRlYzIzIn0.eyJhdWQiOiIyIiwianRpIjoiOTA1MDRlMjdlYmViNDY0M2RkYzkyZGE3YjU2NGNjYWQzMDUzYTIzNWQ3NTQ2MDU4MzBmMzBlOTE0ZjlkZjI4OTc2NzhkYjdiM2MyNGVjMjMiLCJpYXQiOjE1Mzk1OTE1NTQsIm5iZiI6MTUzOTU5MTU1NCwiZXhwIjoxNTcxMTI3NTU0LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.AEE85QrLzTih-HjYQikADJKxZdzho58p3xak7JM9KPgjfEzhGTQD_UB3T-bw5H_bDrnA1OKhxwnFGCFBd0pf57lxRmXkDZExvphmE5-MbrUXTSqytgUOqva2TrqOAz_Sq-1l5pmWpyMGzeiyCECBwUAJHB9bM_0SBqgLTaSQL0SF053pyjgciIbn5GGBKL9M9aAuHC4-L4AczQg_n74xcgAhd0m_Bl3kYUcNNao8TdxzPnlpnPy68Rub2plz-vpk9zXfYaKhO4km1hmKkm7gLsT8X1iiUM8kKVBLnvWjrpai6L8Um9Bfbu7lD1a36cpwwTjZFvYdQtpXZreeJMcRr5LdExwp_mEYIX0CbZGnriTAzRtEThxV-XOWHqRM6RNPHd4UBIIg_xTTQKuCSqeW_p8flAqM7yfl7Snbn_4X7haWyBCJmVG3kOWRv05MPS6vFr129pU4eGYsoIL85hazLJgDKko4ToZXFBCjMfqkGzRqTgll3FXoQK3cBiKkRmejR2fglqy2zGqU3WEkUNuP8k1Mt656CEvpVCMLFPTGCE6nzu5CQra_9MabNGfPzhp34Nso0iQ4cYtTFhwTEY_zXK2J6mfbm8s9dg4wYBOdd9BNHS5T7gG_nnzL0Vajdkom0g9L5L-AyNgvTTkqFQa-Ovq5SJg-Zh36gHIDbe9w610");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }
}
