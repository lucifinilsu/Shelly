/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.shelly.domino.converter;

import android.support.v4.util.Pair;

import retrofit2.Response;
import xiaofei.library.shelly.domino.Domino;
import xiaofei.library.shelly.domino.RetrofitDomino2;
import xiaofei.library.shelly.util.Triple;

/**
 * Created by Xiaofei on 16/6/28.
 */
public class RetrofitDominoConverter2<T, R> implements
        DominoConverter<T, Triple<Boolean, Pair<T, Response<R>>, Throwable>, RetrofitDomino2<T, R>> {
    @Override
    public RetrofitDomino2<T, R> call(Domino<T, Triple<Boolean, Pair<T, Response<R>>, Throwable>> domino) {
        return new RetrofitDomino2<T, R>(domino.getLabel(), domino.getPlayer());
    }
}
