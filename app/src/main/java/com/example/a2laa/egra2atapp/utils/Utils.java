/**
 * Copyright 2010-present Mobily.ws.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.a2laa.egra2atapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	/**
	 * function to check if there is Internet connection ...
	 * 
	 * @return true if there is internet :)
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
		if (networkInfo == null)
			return false;
		if (!networkInfo.isConnected())
			return false;
		if (!networkInfo.isAvailable())
			return false;
		return true;
	}

}
