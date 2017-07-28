package com.theanhdev97.truyenhot.Utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.DefaultRetryPolicy


/**
 * Created by DELL on 05/07/2017.
 */
class Internet() {
    companion object {
        fun getContentStringFromInternetURL(url: String, context: Context, callback: getContentCallback) {
            val queue = Volley.newRequestQueue(context)
            var content: String = ""
            val stringRequest = StringRequest(
                    Request.Method.GET,
                    url,
                    Response.Listener<String> {
                        response ->
                        content = response.toString()
                        callback.onSuccess(content)
                    },
                    Response.ErrorListener {
                        error ->
                        callback.onFailure(error.message!!)
                        Log.d(Constants.TAG, "error : ${error.message}")
                    })
            stringRequest.setRetryPolicy(DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }

    interface getContentCallback {
        fun onSuccess(content: String)
        fun onFailure(error: String)
    }
}