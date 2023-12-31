/*
 * Copyright (c) 2018. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android_jigsaw_puzzle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Wrapper for a Long to pass into an intent and to be retrieved in the sub-sequent activity.
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class LongParcelable
        implements Parcelable {

    public static final Creator<LongParcelable> CREATOR = new
            Creator<LongParcelable>() {
                @Override
                public LongParcelable createFromParcel(Parcel in) {
                    return new LongParcelable(in);
                }

                @Override
                public LongParcelable[] newArray(int size) {
                    return new LongParcelable[size];
                }
            };
    private long data;

    public LongParcelable(long in) {
        data = in;
    }

    private LongParcelable(Parcel in) {
        data = in.readLong();
    }

    public long getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(data);
    }
}
