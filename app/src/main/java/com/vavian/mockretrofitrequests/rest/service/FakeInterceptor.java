/*
 * Copyright (C) 2015. Victor Apoyan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vavian.mockretrofitrequests.rest.service;

import android.os.Build;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.vavian.mockretrofitrequests.BuildConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

/**
 *
 */
public class FakeInterceptor implements Interceptor {

    private final static String TAG = FakeInterceptor.class.getSimpleName();

    // FAKE RESPONSES.
    private final static String TEACHER_ID_1 = "{\"id\":1,\"age\":28,\"name\":\"Victor Apoyan\"}";
    private final static String TEACHER_ID_2 = "{\"id\":1,\"age\":16,\"name\":\"Tovmas Apoyan\"}";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "---- DEBUG --- DEBUG -- DEBUG - DEBUG -- DEBUG --- DEBUG ----");
            Log.d(TAG, "----                FAKE SERVER RESPONSES                ----");
            String responseString;
            // Get Request URI.
            final URI uri = chain.request().uri();
            Log.d(TAG, "---- Request URL: " + uri.toString());
            // Get Query String.
            final String query = uri.getQuery();
            // Parse the Query String.
            final String[] parsedQuery = query.split("=");
            if(parsedQuery[0].equalsIgnoreCase("id") && parsedQuery[1].equalsIgnoreCase("1")) {
                responseString = TEACHER_ID_1;
            }
            else if(parsedQuery[0].equalsIgnoreCase("id") && parsedQuery[1].equalsIgnoreCase("2")){
                responseString = TEACHER_ID_2;
            }
            else {
                responseString = "";
            }

            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();

            Log.d(TAG, "---- DEBUG --- DEBUG -- DEBUG - DEBUG -- DEBUG --- DEBUG ----");
        }
        else {
            response = chain.proceed(chain.request());
        }

        return response;
    }
}
