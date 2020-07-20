/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.dj.ihouseservices;

import java.util.Random;

class DropBack {

    private static final Random RANDOM = new Random();


    static int getRandomChildDrawable(int a) {
        switch (a) {
            default:
            case 0:
                return R.drawable.bg;

            case 1:
                return R.drawable.plumbing;
            case 2:
                return R.drawable.locksmith;
            case 3:
                return R.drawable.vulcanizing;
             case 4:
                return R.drawable.painting;
            case 5:
                return R.drawable.lawn;
            case 6:
                return R.drawable.mechanics;
            case 7:
                return R.drawable.laundry;
            case 8:
                return R.drawable.pool;
             case 9:
                return R.drawable.nanny;
            case 10:
                return R.drawable.pest;

                    }
    }

    }



